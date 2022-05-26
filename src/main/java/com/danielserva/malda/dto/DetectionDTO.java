package com.danielserva.malda.dto;

import java.sql.Timestamp;
import java.util.UUID;

import com.danielserva.malda.model.DetectionType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectionDTO {
    UUID uuid;
    DetectionType type;
    Timestamp time;
    String nameOfApp;
    String typeOfApp;

    @JsonBackReference
    DeviceDTO device;
}
