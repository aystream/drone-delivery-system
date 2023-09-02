package com.musala.services.medication.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Medication {
    @Id
    @NotNull
    @Size(max = 100)
    @Pattern(
            regexp = "[A-Z0-9_]+",
            message = "only upper case letters, underscore and numbers allowed"
    )
    private String code;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    @Pattern(
            regexp = "[a-zA-Z_0-9-]+",
            message = "only letters, numbers, underscore and hyphen allowed"
    )
    private String name;

    @Column(nullable = false)
    private Double weight;

    // TODO use url or something else(minio)
    private String imageUrl;


    private String droneSerialNumber;

    public String getName() {
        return name;
    }

    public Medication setName(String name) {
        this.name = name;
        return this;
    }

    public Double getWeight() {
        return weight;
    }

    public Medication setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Medication setCode(String code) {
        this.code = code;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Medication setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDroneSerialNumber() {
        return droneSerialNumber;
    }

    public Medication setDroneSerialNumber(String droneSerialNumber) {
        this.droneSerialNumber = droneSerialNumber;
        return this;
    }
}