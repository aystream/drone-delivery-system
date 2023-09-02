package com.musala.services.drone.config;

import com.musala.services.drone.jobs.BatteryCheckJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    private final DroneProperties droneProperties;

    public QuartzConfig(DroneProperties droneProperties) {
        this.droneProperties = droneProperties;
    }

    @Bean
    public JobDetail batteryCheckJobDetail() {
        return JobBuilder.newJob(BatteryCheckJob.class)
                .withIdentity("batteryCheckJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger batteryCheckJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(droneProperties.getBatteryCheckInterval())
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(batteryCheckJobDetail())
                .withIdentity("batteryCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}