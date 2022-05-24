package com.danielserva.malda.dto;

import java.util.Set;
import java.util.UUID;

import com.danielserva.malda.model.DeviceType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDTO {
    
    Long id;
    
    UUID uuID;
    DeviceType type;
    String model;
    String osVersion;
    @JsonManagedReference
    Set<DetectionDTO> detections;
}
