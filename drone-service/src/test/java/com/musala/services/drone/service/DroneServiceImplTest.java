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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneMapper droneMapper;

    @Mock
    private DroneProperties droneProperties;

    @Mock
    private StateMachine<DroneState, DroneEvent> stateMachine;

    @Mock
    private State<DroneState, DroneEvent> state;

    @Mock
    private MedicationClient medicationClient;

    @InjectMocks
    private DroneServiceImpl droneService;

    @Test
    void testRegisterDrone() {
        var droneDTO = new DroneDTO();
        Drone drone = new Drone();
        when(droneMapper.toEntity(droneDTO)).thenReturn(drone);
        when(droneRepository.save(drone)).thenReturn(drone);
        when(droneMapper.toDTO(drone)).thenReturn(droneDTO);

        var result = droneService.registerDrone(droneDTO);
        assertEquals(droneDTO, result);
    }

    @Test
    void testGetDroneNotFound() {
        String serialNumber = "testSerial";
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(DroneErrorException.class, () -> droneService.getDrone(serialNumber));
    }

    @Test
    void testGetAllDrones() {
        Pageable pageable = mock(Pageable.class);
        Map<String, String> searchParams = new HashMap<>();
        var droneDTO = new DroneDTO();
        Page<Drone> dronePage = mock(Page.class);
        when(dronePage.map(any())).thenReturn(mock(Page.class));

        when(droneRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(dronePage);

        var result = droneService.getAllDrones(searchParams, pageable);
        assertNotNull(result);
    }

    @Test
    void testLoadDroneWithMedications() {
        String serialNumber = "testSerial";
        List<String> medicationCodes = Collections.singletonList("code1");
        Drone drone = new Drone();
        drone.setState(DroneState.IDLE);
        drone.setCurrentWeight(10.0);
        drone.setWeightLimit(20.0);

        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setWeight(5.0);
        when(stateMachine.getState()).thenReturn(state);
        when(state.getId()).thenReturn(DroneState.LOADING);
        when(droneRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(drone));
        when(medicationClient.getMedicationsByCodes(medicationCodes)).thenReturn(Collections.singletonList(medicationDTO));

        droneService.loadDroneWithMedications(serialNumber, medicationCodes);
        assertEquals(DroneState.LOADING, drone.getState());
    }

}