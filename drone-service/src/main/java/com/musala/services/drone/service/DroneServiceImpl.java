package com.musala.services.drone.service;

import com.musala.services.drone.config.DroneProperties;
import com.musala.services.drone.exception.DroneErrorException;
import com.musala.services.drone.feignclient.MedicationClient;
import com.musala.services.drone.mapper.DroneMapper;
import com.musala.services.drone.model.dto.DroneDTO;
import com.musala.services.drone.model.dto.MedicationDTO;
import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.model.enums.DroneState;
import com.musala.services.drone.repository.DroneRepository;
import com.musala.services.drone.repository.DroneSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DroneServiceImpl implements DroneService {
    private static final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class.getName());

    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;
    private final DroneProperties droneProperties;
    private final StateMachine<DroneState, DroneEvent> stateMachine;
    private final MedicationClient medicationClient;

    public DroneServiceImpl(DroneRepository droneRepository,
                            DroneMapper droneMapper,
                            DroneProperties droneProperties,
                            StateMachine<DroneState, DroneEvent> stateMachine, MedicationClient medicationClient) {
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
        this.droneProperties = droneProperties;
        this.stateMachine = stateMachine;
        this.medicationClient = medicationClient;
    }

    @Override
    public DroneDTO registerDrone(DroneDTO droneDto) {
        log.debug("Registering drone with details: {}", droneDto);
        Drone drone = droneMapper.toEntity(droneDto);
        Drone savedDrone = droneRepository.save(drone);
        return droneMapper.toDTO(savedDrone);
    }

    @Override
    public DroneDTO getDrone(String serialNumber) {
        log.debug("Fetching drone with serial number: {}", serialNumber);
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> {
                    log.error("Drone with serial number {} not found", serialNumber);
                    return new DroneErrorException("Drone with serial number " + serialNumber + " not found");
                });
        return droneMapper.toDTO(drone);
    }

    @Override
    public Page<DroneDTO> getAllDrones(Map<String, String> searchParams, Pageable pageable) {
        Specification<Drone> spec = DroneSpecification.searchAndFilter(searchParams);
        Page<Drone> drones = droneRepository.findAll(spec, pageable);
        return drones.map(droneMapper::toDTO);
    }

    @Override
    public Page<Drone> getAvailableDronesForLoad(Pageable pageable) {
        log.debug("Fetching available drones for load with pagination: {}", pageable);
        return droneRepository.findByStateAndBatteryCapacityGreaterThan(DroneState.IDLE, droneProperties.getBatteryThreshold(), pageable);
    }

    @Override
    public void loadDroneWithMedications(String serialNumber, List<String> medicationCodes) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> {
                    log.error("Drone with serial number {} not found", serialNumber);
                    return new DroneErrorException("Drone with serial number " + serialNumber + " not found");
                });
        // 1. Validate the Drone
        if (!DroneState.IDLE.equals(drone.getState())) {
            throw new DroneErrorException("Drone is not in the IDLE state and cannot be loaded.");
        }

        // 2. Retrieve Medications
        List<MedicationDTO> medications = medicationClient.getMedicationsByCodes(medicationCodes);

        // 3. Validate Medications
        double totalMedicationWeight = medications.stream().mapToDouble(MedicationDTO::weight).sum();
        if (totalMedicationWeight + drone.getCurrentWeight() > drone.getWeightLimit()) {
            throw new DroneErrorException("The total weight of the medications exceeds the drone's weight limit.");
        }

        // 4. Update the Drone's State
        stateMachine.sendEvent(DroneEvent.LOAD);
        drone.setState(stateMachine.getState().getId());

        // 5. Associate Medications with the Drone
        medicationClient.setMedicationsByDroneSerialNumber(medicationCodes, drone.getSerialNumber());

        drone.setCurrentWeight(drone.getCurrentWeight() + totalMedicationWeight);
        droneRepository.save(drone);
    }

    @Override
    public DroneDTO changeDroneEvent(String serialNumber, DroneEvent event) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> {
                    log.error("Drone with serial number {} not found", serialNumber);
                    return new DroneErrorException("Drone with serial number " + serialNumber + " not found");
                });
        stateMachine.sendEvent(event);
        drone.setState(stateMachine.getState().getId());
        Drone savedDrone = droneRepository.save(drone);
        return droneMapper.toDTO(savedDrone);
    }

    @Override
    public Map<String, Object> getDroneBatteryAndState(String serialNumber) {
        DroneDTO drone = getDrone(serialNumber);
        Map<String, Object> droneInfo = new HashMap<>();
        droneInfo.put("batteryCapacity", drone.getBatteryCapacity());
        droneInfo.put("state", drone.getState());
        return droneInfo;
    }
}
