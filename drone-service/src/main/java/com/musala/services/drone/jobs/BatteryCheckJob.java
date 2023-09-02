package com.musala.services.drone.jobs;

import com.musala.services.drone.config.DroneProperties;
import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.repository.DroneRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryCheckJob implements Job {
    private final Logger log = LoggerFactory.getLogger(BatteryCheckJob.class.getName());
    private final DroneRepository droneRepository;
    private final DroneProperties droneProperties;

    public BatteryCheckJob(DroneRepository droneRepository, DroneProperties droneProperties) {
        this.droneRepository = droneRepository;
        this.droneProperties = droneProperties;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones) {
            if (drone.getBatteryCapacity() < droneProperties.getBatteryThreshold()) {
                // Log or create an audit event that the drone's battery is below 25%
                // This is just a simple logging. You might want to store this in an audit table or send notifications.
                log.warn("Drone with serial number {} has battery level below 25%", drone.getSerialNumber());
            }
        }
    }
}