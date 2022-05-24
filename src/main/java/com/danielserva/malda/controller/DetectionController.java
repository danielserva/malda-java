package com.danielserva.malda.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.danielserva.malda.dto.DetectionDTO;
import com.danielserva.malda.dto.DeviceDTO;
import com.danielserva.malda.exception.DetectionNotFoundException;
import com.danielserva.malda.model.Detection;
import com.danielserva.malda.model.DetectionRepository;
import com.danielserva.malda.model.DetectionType;
import com.danielserva.malda.model.Device;
import com.danielserva.malda.model.DeviceRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectionController {

    private final DetectionRepository detectionRepository;
    private final DeviceRepository deviceRepository;
    
    private final DetectionDTOAssembler dtoAssembler;
    private final ModelMapper modelMapper;
    
    DetectionController(DetectionRepository repository, DeviceRepository deviceRepository, DetectionDTOAssembler dtoAssembler, ModelMapper modelMapper){
        this.detectionRepository = repository;
        this.deviceRepository = deviceRepository;
        this.dtoAssembler = dtoAssembler;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping("/detection/{uuId}")
    EntityModel<DetectionDTO> getByUuid(@PathVariable UUID uuId){
        Detection detection = detectionRepository.findOneByUuid(uuId)
        .orElseThrow(() -> new DetectionNotFoundException(uuId));
        DetectionDTO detectionDto = modelMapper.map(detection, DetectionDTO.class);
        return dtoAssembler.toModel(detectionDto);
    }
    
    @GetMapping("/detection/id/{id}")
    EntityModel<DetectionDTO> getById(@PathVariable Long id){
        Detection detection = detectionRepository.findById(id)
        .orElseThrow(() -> new DetectionNotFoundException(id));
        DetectionDTO detectionDto = modelMapper.map(detection, DetectionDTO.class);
        return dtoAssembler.toModel(detectionDto);
    }
    
    
    @GetMapping("/detection")
    CollectionModel<EntityModel<DetectionDTO>> all(){
        List<EntityModel<DetectionDTO>> detections = detectionRepository.findAll(Sort.by(Direction.ASC, "time"))
            .stream()
            .map(d -> modelMapper.map(d, DetectionDTO.class))
            .map(dtoAssembler::toModel)
            .collect(Collectors.toList()) ;
        return CollectionModel.of(detections, 
            linkTo(methodOn(DetectionController.class).all()).withSelfRel() ) ;
    }

    @PostMapping("/detection")
    Detection newDetection(@RequestBody Detection newDetection){
        return detectionRepository.save(newDetection);
    }

    /** 
     * A detection message should contain 2 fields:
     * 1- A device information 
     * 2- Detection information
     * A device can have one, several or no detections.
     * A DeviceDTO satisfies the requirements for this message, thus
     * we will reuse this to keep system design simple.
     * */ 
    @PostMapping("/detectionmessage")
    Device newDetection(@RequestBody DeviceDTO detectionMessage){
        Device newDeviceDetection = modelMapper.map(detectionMessage, Device.class);
        return deviceRepository.save(newDeviceDetection);
    }

    @PutMapping("/detection")
    Detection updateDetection(@RequestBody Detection detection){
        Detection d = detectionRepository.findOneByUuid(detection.getUuid())
        .orElseThrow(() -> new DetectionNotFoundException(detection.getUuid()));
        d.setType(DetectionType.RESOLVED);
        d.setTime(detection.getTime());
        return detectionRepository.save(d);
    }

    @PostMapping("/detections")
    List<Detection> newDetection(@RequestBody List<Detection> newDetections){
        return detectionRepository.saveAll(newDetections);
    }
}
