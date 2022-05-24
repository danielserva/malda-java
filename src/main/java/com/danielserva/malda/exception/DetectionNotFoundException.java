package com.danielserva.malda.exception;

import java.util.UUID;

public class DetectionNotFoundException extends RuntimeException {
    public DetectionNotFoundException(Long id){
        super("Could not find detection with id: " + id);
    }
    public DetectionNotFoundException(UUID uuid){
        super("Could not find detection with uuid: " + uuid);
    }
}
