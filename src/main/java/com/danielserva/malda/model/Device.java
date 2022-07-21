package com.danielserva.malda.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Device {

    public Device(){

    }

    public Device(UUID uuid, DeviceType type, String model, String osVersion){ 
        this.uuid = uuid; 
        this.type = type;
        this.model = model;
        this.osVersion = osVersion;

    }
    
    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @NotNull(message = "Device uuid must not be null")
    private UUID uuid;
    @NotNull
    private DeviceType type;
    @NotBlank(message = "Model is mandatory")
    private String model;
    @NotBlank(message = "Os version is mandatory")
    private String osVersion;

    @OneToMany(mappedBy = "device", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Detection> detections;

}
