package com.danielserva.malda.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetectionMessageDTO {
    DeviceDTO deviceInformation;
    List<DetectionDTO> detectionInformation;

    
}
