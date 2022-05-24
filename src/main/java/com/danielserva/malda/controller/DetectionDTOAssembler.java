package com.danielserva.malda.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.danielserva.malda.dto.DetectionDTO;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DetectionDTOAssembler implements RepresentationModelAssembler<DetectionDTO, EntityModel<DetectionDTO>> {

    @Override
    public EntityModel<DetectionDTO> toModel(DetectionDTO detection) {
        return EntityModel.of(detection,
        linkTo(methodOn(DetectionController.class).getByUuid(detection.getUuid())).withSelfRel(),
        linkTo(methodOn(DetectionController.class).all()).withRel("detections")) ;
    }

}
