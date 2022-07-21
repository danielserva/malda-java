package com.danielserva.malda.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Detection {
    
    public Detection(){

    }

    public Detection(UUID detectionId, Timestamp time, String nameOfApp, String typeOfApp){
        this.type = DetectionType.NEW;
        this.uuid = detectionId;
        this.time = time;
        this.nameOfApp = nameOfApp;
        this.typeOfApp = typeOfApp; 
    }
    
    @Id
    @GeneratedValue
    private Long id;

    @Type(type="org.hibernate.type.UUIDCharType")
    @NotNull(message = "Detection uuid must not be null")
    private UUID uuid;
    @NotNull(message = "Detection type must not be null")
    private DetectionType type;
    @NotNull(message = "Detection time must not be null")
    private Timestamp time;
    private String nameOfApp;
    private String typeOfApp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Device device;

    @AssertTrue(message = "Name and Type of App are mandatory")
    private boolean isOk(){
        return (this.type == DetectionType.RESOLVED || this.type == DetectionType.NO_DETECTION ) || (null != this.nameOfApp && null!= this.typeOfApp) ; 
    }
    
}
