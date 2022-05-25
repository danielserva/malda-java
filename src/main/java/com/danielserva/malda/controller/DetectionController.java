package com.danielserva.malda.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.danielserva.malda.dto.DetectionDTO;
import com.danielserva.malda.dto.DetectionMessageDTO;
import com.danielserva.malda.dto.DeviceDTO;
import com.danielserva.malda.exception.DetectionNotFoundException;
import com.danielserva.malda.model.Detection;
import com.danielserva.malda.model.DetectionRepository;
import com.danielserva.malda.model.Device;
import com.danielserva.malda.model.DeviceRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectionController {

    private static final Logger log = LoggerFactory.getLogger(DetectionController.class);
    private final DetectionRepository detectionRepository;
    private final DeviceRepository deviceRepository;
    
    private final DetectionDTOAssembler detectionDtoAssembler;
    private final DeviceDTOAssembler deviceDtoAssembler;
    private final ModelMapper modelMapper;
    
    DetectionController(DetectionRepository repository, DeviceRepository deviceRepository, DetectionDTOAssembler dtoAssembler, DeviceDTOAssembler deviceDtoAssembler, ModelMapper modelMapper){
        this.detectionRepository = repository;
        this.deviceRepository = deviceRepository;
        this.detectionDtoAssembler = dtoAssembler;
        this.deviceDtoAssembler = deviceDtoAssembler;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/detection")
    CollectionModel<EntityModel<DetectionDTO>> all(){
        List<EntityModel<DetectionDTO>> detections = detectionRepository.findAll(Sort.by(Direction.ASC, "time"))
            .stream()
            .map(d -> modelMapper.map(d, DetectionDTO.class))
            .map(detectionDtoAssembler::toModel)
            .collect(Collectors.toList()) ;
        return CollectionModel.of(detections, 
            linkTo(methodOn(DetectionController.class).all()).withSelfRel() ) ;
    }
    
    @GetMapping("/detection/{uuId}")
    EntityModel<DetectionDTO> getByUuid(@PathVariable UUID uuId){
        Detection detection = detectionRepository.findOneByUuid(uuId)
        .orElseThrow(() -> new DetectionNotFoundException(uuId));
        DetectionDTO detectionDto = modelMapper.map(detection, DetectionDTO.class);
        return detectionDtoAssembler.toModel(detectionDto);
    }
    
    // @GetMapping("/detection/id/{id}")
    // EntityModel<DetectionDTO> getById(@PathVariable Long id){
    //     Detection detection = detectionRepository.findById(id)
    //     .orElseThrow(() -> new DetectionNotFoundException(id));
    //     DetectionDTO detectionDto = modelMapper.map(detection, DetectionDTO.class);
    //     return dtoAssembler.toModel(detectionDto);
    // }
    
    @PostMapping("/detection")
    ResponseEntity<?>  newDetection(@RequestBody DetectionDTO newDetectionDto){
        Detection newDetection = modelMapper.map(newDetectionDto, Detection.class);
        detectionRepository.save(newDetection);
        newDetectionDto = modelMapper.map(newDetection, DetectionDTO.class);
        EntityModel<DetectionDTO> detectionEntity = detectionDtoAssembler.toModel(newDetectionDto);
        return ResponseEntity
            .created(detectionEntity.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(detectionEntity) ;
    }

    /** 
     * A detection message should contain the following:
     * 1- Device information 
     * 2- Detection information
     * A device can have one, several or no detections.
     * A DeviceDTO satisfies the requirements for this message, thus
     * we will reuse this to keep system design simple.
     * */ 
    @PostMapping("/detectionmessage")
    ResponseEntity<?> create(@RequestBody DeviceDTO newDeviceDto){
        log.info("saving device");
        Device newDevice = modelMapper.map(newDeviceDto, Device.class);
        deviceRepository.save(newDevice);
        newDeviceDto =  modelMapper.map(newDevice, DeviceDTO.class);
        EntityModel<DeviceDTO> entityModel = deviceDtoAssembler.toModel(newDeviceDto);
        return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }
        
    // @PostMapping("/detectionmessage")
    Device newDetection(@RequestBody DetectionMessageDTO detectionMessage){
        Device newDeviceDetection = modelMapper.map(detectionMessage.getDeviceInformation(), Device.class);
        List<Detection> detectionList = detectionMessage.getDetectionInformation()
                                        .stream()
                                        .map(di -> modelMapper.map(di, Detection.class))
                                        .collect(Collectors.toList());
        return deviceRepository.save(newDeviceDetection);
    }
}
