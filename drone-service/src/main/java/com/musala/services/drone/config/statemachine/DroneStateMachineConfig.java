package com.musala.services.drone.config.statemachine;

import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.model.enums.DroneState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class DroneStateMachineConfig extends EnumStateMachineConfigurerAdapter<DroneState, DroneEvent> {

    private final ResetDroneWeightAction resetDroneWeightAction;

    public DroneStateMachineConfig(ResetDroneWeightAction resetDroneWeightAction) {
        this.resetDroneWeightAction = resetDroneWeightAction;
    }

    @Override
    public void configure(StateMachineStateConfigurer<DroneState, DroneEvent> states) throws Exception {
        states
                .withStates()
                .initial(DroneState.IDLE)
                .states(EnumSet.allOf(DroneState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DroneState, DroneEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(DroneState.IDLE).target(DroneState.LOADING).event(DroneEvent.LOAD)
                .and()
                .withExternal()
                .source(DroneState.LOADING).target(DroneState.LOADED).event(DroneEvent.LOADED)
                .and()
                .withExternal()
                .source(DroneState.LOADED).target(DroneState.DELIVERING).event(DroneEvent.DELIVER)
                .and()
                .withExternal()
                .source(DroneState.DELIVERING).target(DroneState.DELIVERED).event(DroneEvent.DELIVERED)
                .and()
                .withExternal()
                .source(DroneState.DELIVERED).target(DroneState.RETURNING).event(DroneEvent.RETURN)
                .and()
                .withExternal()
                .source(DroneState.RETURNING).target(DroneState.IDLE).event(DroneEvent.IDLE).action(resetDroneWeightAction);
    }
}
