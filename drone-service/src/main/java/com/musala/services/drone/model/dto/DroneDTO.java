package com.musala.services.drone.model.dto;

import com.musala.services.drone.model.enums.DroneModel;
import com.musala.services.drone.model.enums.DroneState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DroneDTO {
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    @Max(500)
    private Double weightLimit;
    @Max(500)
    private Double currentWeight;
    @Max(100)
    private Double batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState state = DroneState.IDLE;

    public String getSerialNumber() {
        return serialNumber;
    }

    public DroneDTO setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public DroneModel getModel() {
        return model;
    }

    public DroneDTO setModel(DroneModel model) {
        this.model = model;
        return this;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public DroneDTO setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
        return this;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public DroneDTO setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
        return this;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public DroneDTO setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public DroneState getState() {
        return state;
    }

    public DroneDTO setState(DroneState state) {
        this.state = state;
        return this;
    }
}
