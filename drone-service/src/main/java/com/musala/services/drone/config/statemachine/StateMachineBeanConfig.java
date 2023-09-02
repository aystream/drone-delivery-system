package com.musala.services.drone.config.statemachine;

import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.model.enums.DroneState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@Configuration
public class StateMachineBeanConfig {

    private final StateMachineFactory<DroneState, DroneEvent> stateMachineFactory;

    public StateMachineBeanConfig(StateMachineFactory<DroneState, DroneEvent> stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

    @Bean
    public StateMachine<DroneState, DroneEvent> stateMachine() {
        StateMachine<DroneState, DroneEvent> stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.startReactively();
        return stateMachine;
    }
}