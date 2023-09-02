package com.musala.services.drone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "drone")
public class DroneProperties {
    private Double batteryThreshold;
    private Long batteryCheckInterval;

    public Double getBatteryThreshold() {
        return batteryThreshold;
    }

    public void setBatteryThreshold(Double batteryThreshold) {
        this.batteryThreshold = batteryThreshold;
    }

    public Long getBatteryCheckInterval() {
        return batteryCheckInterval;
    }

    public void setBatteryCheckInterval(Long batteryCheckInterval) {
        this.batteryCheckInterval = batteryCheckInterval;
    }
}
