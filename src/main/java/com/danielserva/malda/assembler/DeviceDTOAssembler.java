package com.danielserva.malda.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.danielserva.malda.controller.DeviceController;
import com.danielserva.malda.dto.DeviceDTO;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DeviceDTOAssembler implements RepresentationModelAssembler<DeviceDTO, EntityModel<DeviceDTO>> {

    @Override
    public EntityModel<DeviceDTO> toModel(DeviceDTO device) {
        return EntityModel.of(device,
        linkTo(methodOn(DeviceController.class).getByUuid(device.getUuid())).withSelfRel(),
        linkTo(methodOn(DeviceController.class).all()).withRel("devices")) ;
    }

}
