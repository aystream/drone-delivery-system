package com.musala.services.drone.model.dto;

public class MedicationDTO {
    private String code;
    private Double weight;

    public String code() {
        return code;
    }

    public MedicationDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public Double weight() {
        return weight;
    }

    public MedicationDTO setWeight(Double weight) {
        this.weight = weight;
        return this;
    }
}
