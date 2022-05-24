package com.danielserva.malda.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;


@Entity
public class Device {

    public Device(){

    }

    public Device(UUID uuid, DeviceType type, String model, String osVersion){ 
        this.uuid = uuid; 
        this.type = type;
        this.model = model;
        this.osVersion = osVersion;
        // this.detections = detections;
    }
    
    @Id
    @GeneratedValue
    @Getter
    private Long id;
    
    @Getter
    private UUID uuid;
    @Getter
    private DeviceType type;
    @Getter
    private String model;
    @Getter
    private String osVersion;

    @Getter
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private Set<Detection> detections;

}
