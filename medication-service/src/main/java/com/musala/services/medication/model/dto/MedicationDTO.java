package com.musala.services.medication.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class MedicationDTO {
    @Pattern(
            regexp = "[A-Z0-9_]+",
            message = "only upper case letters, underscore and numbers allowed"
    )
    private String code;
    @Pattern(
            regexp = "[a-zA-Z_0-9-]+",
            message = "only letters, numbers, underscore and hyphen allowed"
    )
    private String name;

    @Positive
    private Double weight;

    private String imageUrl;

    private String droneSerialNumber;

    public String name() {
        return name;
    }

    public String getName() {
        return name;
    }

    public MedicationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Double getWeight() {
        return weight;
    }

    public MedicationDTO setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public String getCode() {
        return code;
    }

    public MedicationDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MedicationDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDroneSerialNumber() {
        return droneSerialNumber;
    }

    public MedicationDTO setDroneSerialNumber(String droneSerialNumber) {
        this.droneSerialNumber = droneSerialNumber;
        return this;
    }
}
