package com.musala.services.drone.jobs;

import com.musala.services.drone.config.DroneProperties;
import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatteryCheckJobTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneProperties droneProperties;

    @Mock
    private Logger log;

    @Captor
    private ArgumentCaptor<String> logCaptor;

    @InjectMocks
    private BatteryCheckJob batteryCheckJob;

    @Test
    public void testExecute_batteryAbove25_percent() throws JobExecutionException {
        List<Drone> drones = new ArrayList<>();
        Drone drone = new Drone();
        drone.setSerialNumber("456");
        drone.setBatteryCapacity(30.0);
        drones.add(drone);

        when(droneRepository.findAll()).thenReturn(drones);

        batteryCheckJob.execute(null);

        verify(log, never()).warn(anyString());
    }
}