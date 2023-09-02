package com.musala.services.drone.service;

import com.musala.services.drone.model.dto.DroneDTO;
import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.model.enums.DroneEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DroneService {
    DroneDTO registerDrone(DroneDTO droneDto);

    DroneDTO getDrone(String serialNumber);

    Page<DroneDTO> getAllDrones(Map<String, String> searchParams, Pageable pageable);

    Page<Drone> getAvailableDronesForLoad(Pageable pageable);

    void loadDroneWithMedications(String serialNumber, List<String> medicationCodes);

    DroneDTO changeDroneEvent(String serialNumber, DroneEvent event);

    Map<String, Object> getDroneBatteryAndState(String serialNumber);
}
