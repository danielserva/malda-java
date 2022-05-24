package com.danielserva.malda.exception;

import java.util.UUID;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(UUID uuid){
        super("Could not find device with uuid: " + uuid);
    }
}
