package com.danielserva.malda.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.danielserva.malda.dto.DeviceDTO;
import com.danielserva.malda.exception.DeviceNotFoundException;
import com.danielserva.malda.model.DetectionRepository;
import com.danielserva.malda.model.Device;
import com.danielserva.malda.model.DeviceRepository;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {
    
    private final DetectionRepository detectionRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceDTOAssembler dtoAssembler;
    private final ModelMapper modelMapper;

    DeviceController(DetectionRepository detectionRepository, DeviceRepository deviceRepository, DeviceDTOAssembler dtoAssembler, ModelMapper modelMapper){
        this.detectionRepository = detectionRepository;
        this.deviceRepository = deviceRepository;
        this.dtoAssembler = dtoAssembler;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/device/{uuId}")
    EntityModel<DeviceDTO> getByUuid(@PathVariable UUID uuId){
        Device device = deviceRepository.findOneByUuid(uuId)
        .orElseThrow(() -> new DeviceNotFoundException(uuId));
        DeviceDTO deviceDto = modelMapper.map(device, DeviceDTO.class);
        return dtoAssembler.toModel(deviceDto);
    }

    @GetMapping("/device")
    CollectionModel<EntityModel<DeviceDTO>> all(){
        List<EntityModel<DeviceDTO>> devices = deviceRepository.findAll()
            .stream()
            .map(d -> modelMapper.map(d, DeviceDTO.class))
            .map(dtoAssembler::toModel)
            .collect(Collectors.toList()) ;
        return CollectionModel.of(devices, 
            linkTo(methodOn(DeviceController.class).all()).withSelfRel() ) ;
    }
}
