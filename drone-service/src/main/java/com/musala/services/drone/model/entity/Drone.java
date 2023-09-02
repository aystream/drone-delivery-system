package com.musala.services.drone.model.entity;

import com.musala.services.drone.model.enums.DroneModel;
import com.musala.services.drone.model.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @Column(length = 100, nullable = false)
    @Pattern(
            regexp = "[A-Z0-9_]+",
            message = "only upper case letters, underscore and numbers allowed"
    )
    @NotNull(message = "Please enter a serial number for the drone")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Column(nullable = false, columnDefinition = "double default 0")
    @Max(value = 500, message = "Total weight limit exceeded")
    private Double currentWeight;

    @Column(nullable = false)
    @Max(value = 500, message = "Total weight limit exceeded")
    private Double weightLimit;

    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) default 'IDLE'")
    private DroneState state;

    public String getSerialNumber() {
        return serialNumber;
    }

    public Drone setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public DroneModel getModel() {
        return model;
    }

    public Drone setModel(DroneModel model) {
        this.model = model;
        return this;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public Drone setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
        return this;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public Drone setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
        return this;
    }

    public Double getBatteryCapacity() {
        return batteryCapacity;
    }

    public Drone setBatteryCapacity(Double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public DroneState getState() {
        return state;
    }

    public Drone setState(DroneState state) {
        this.state = state;
        return this;
    }
}

