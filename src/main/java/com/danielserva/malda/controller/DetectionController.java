package com.danielserva.malda.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.danielserva.malda.assembler.DetectionDTOAssembler;
import com.danielserva.malda.assembler.DeviceDTOAssembler;
import com.danielserva.malda.dto.DetectionDTO;
import com.danielserva.malda.dto.DeviceDTO;
import com.danielserva.malda.model.Detection;
import com.danielserva.malda.model.DetectionRepository;
import com.danielserva.malda.model.Device;
import com.danielserva.malda.model.DeviceRepository;

@RestController
public class DetectionController {

    
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
    public CollectionModel<EntityModel<DetectionDTO>> all(){
        List<EntityModel<DetectionDTO>> detections = detectionRepository.findAll(Sort.by(Direction.DESC, "time"))
            .stream()
            .map(d -> modelMapper.map(d, DetectionDTO.class))
            .map(detectionDtoAssembler::toModel)
            .collect(Collectors.toList()) ;
        return CollectionModel.of(detections, 
            linkTo(methodOn(DetectionController.class).all()).withSelfRel() ) ;
    }

    @GetMapping("/detection/search")
    public CollectionModel<EntityModel<DetectionDTO>> searchByExample(DetectionDTO detectionDTO){
        Detection detection = modelMapper.map(detectionDTO, Detection.class);
        ExampleMatcher matcher = ExampleMatcher.matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Detection> example = Example.of(detection, matcher);

        List<EntityModel<DetectionDTO>> detections = detectionRepository.findAll(example, Sort.by(Direction.DESC, "time"))
            .stream()
            .map(d -> modelMapper.map(d, DetectionDTO.class))
            .map(detectionDtoAssembler::toModel)
            .collect(Collectors.toList()) ;
        return CollectionModel.of(detections, 
            linkTo(methodOn(DetectionController.class).all()).withSelfRel() ) ;
    }

    
    @GetMapping("/detection/{uuId}")
    public CollectionModel<EntityModel<DetectionDTO>> getByUuid(@PathVariable UUID uuId){
        List<EntityModel<DetectionDTO>> detections = detectionRepository.findByUuid(uuId)
            .stream()
            .map(d -> modelMapper.map(d, DetectionDTO.class))
            .map(detectionDtoAssembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(detections, 
            linkTo(methodOn(DetectionController.class).getByUuid(uuId)).withSelfRel() ) ;
    }
    
    /** 
     * A detection message should contain the following:
     * 1- Device information 
     * 2- Detection information
     * A device can have one, several or no detections.
     * Since a DeviceDTO satisfies the requirements for this message,
     * we will reuse it to keep system design simple.
     * */ 
    @PostMapping("/detection")
    public ResponseEntity<?> create(@RequestBody DeviceDTO deviceDTO){
        Device newDevice = modelMapper.map(deviceDTO, Device.class) ;
        try {
            deviceRepository.save(newDevice);
        } catch (Exception e) {
            throw new ResponseStatusException(
           HttpStatus.BAD_REQUEST, "Could not create detection", e);
        }

        deviceDTO =  modelMapper.map(newDevice, DeviceDTO.class);
        EntityModel<DeviceDTO> entityModel = deviceDtoAssembler.toModel(deviceDTO);
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

        
}
