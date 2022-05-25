package com.danielserva.malda.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.danielserva.malda.dto.DeviceDTO;
import com.danielserva.malda.exception.DeviceNotFoundException;
import com.danielserva.malda.model.Device;
import com.danielserva.malda.model.DeviceRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DeviceController {
    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);
    private final DeviceRepository deviceRepository;
    private final DeviceDTOAssembler deviceDtoAssembler;
    private final ModelMapper modelMapper;

    DeviceController(DeviceRepository deviceRepository, DeviceDTOAssembler dtoAssembler, ModelMapper modelMapper){
        this.deviceRepository = deviceRepository;
        this.deviceDtoAssembler = dtoAssembler;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/device")
    CollectionModel<EntityModel<DeviceDTO>> all(){
        List<EntityModel<DeviceDTO>> devices = deviceRepository.findAll()
            .stream()
            .map(d -> modelMapper.map(d, DeviceDTO.class))
            .map(deviceDtoAssembler::toModel)
            .collect(Collectors.toList()) ;
        return CollectionModel.of(devices, 
            linkTo(methodOn(DeviceController.class).all()).withSelfRel() ) ;
    }

    @GetMapping("/device/{uuId}")
    EntityModel<DeviceDTO> getByUuid(@PathVariable UUID uuId){
        Device device = deviceRepository.findOneByUuid(uuId)
        .orElseThrow(() -> new DeviceNotFoundException(uuId));
        DeviceDTO deviceDto = modelMapper.map(device, DeviceDTO.class);
        return deviceDtoAssembler.toModel(deviceDto);
    }

    @PostMapping("/device")
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
}
