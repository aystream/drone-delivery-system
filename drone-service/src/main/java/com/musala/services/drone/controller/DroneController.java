package com.musala.services.drone.controller;

import com.musala.services.drone.model.dto.DroneDTO;
import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @Operation(summary = "Registers a new drone")
    @PostMapping
    public ResponseEntity<DroneDTO> registerDrone(@Valid @RequestBody DroneDTO droneDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(droneService.registerDrone(droneDto));
    }

    @Operation(summary = "Get a drone by serial number")
    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDTO> getDrone(@PathVariable String serialNumber) {
        return ResponseEntity.ok().body(droneService.getDrone(serialNumber));
    }

    @Operation(summary = "Get all the drones with optional search and filter")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<DroneDTO>> getAllDrones(
            @ParameterObject Pageable pageable,
            @RequestParam Map<String, String> searchParams) {
        return ResponseEntity.ok(droneService.getAllDrones(searchParams, pageable));
    }

    @PutMapping("/{serialNumber}/event")
    @Operation(summary = "Change drone state")
    public ResponseEntity<DroneDTO> changeDroneEvent(@PathVariable String serialNumber, @Valid @RequestBody DroneEvent event) {
        DroneDTO updatedDroneDTO = droneService.changeDroneEvent(serialNumber, event);
        return ResponseEntity.ok(updatedDroneDTO);
    }

    @GetMapping("/{serialNumber}/battery-state")
    @Operation(summary = "Get drone battery and state")
    public ResponseEntity<Map<String, Object>> getDroneBatteryAndState(@PathVariable String serialNumber) {
        Map<String, Object> droneInfo = droneService.getDroneBatteryAndState(serialNumber);
        return ResponseEntity.ok(droneInfo);
    }

    @Operation(summary = "Get available drones to load")
    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Drone>> getAvailableDronesForLoad(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(droneService.getAvailableDronesForLoad(pageable));
    }

    @Operation(summary = "This will display the battery level of a drone")
    @GetMapping("/{serialNumber}/load")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> loadDroneWithMedications(@PathVariable String serialNumber,
                                                           @RequestBody List<String> medicationCodes) {
        droneService.loadDroneWithMedications(serialNumber, medicationCodes);
        return ResponseEntity.ok("Medications loaded successfully.");
    }


}
