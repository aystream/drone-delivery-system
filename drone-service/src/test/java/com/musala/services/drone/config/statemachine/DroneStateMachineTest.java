package com.musala.services.drone.config.statemachine;

import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.model.enums.DroneState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DroneStateMachineTest {

    @Autowired
    private StateMachineFactory<DroneState, DroneEvent> stateMachineFactory;

    private StateMachine<DroneState, DroneEvent> stateMachine;

    @BeforeEach
    void setUp() {
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.start();
    }

    @Test
    public void testInitialState() {
        assertEquals(DroneState.IDLE, stateMachine.getState().getId());
    }

    @Test
    public void whenLoadEvent_thenStateIsLoading() {
        stateMachine.sendEvent(DroneEvent.LOAD);
        assertEquals(DroneState.LOADING, stateMachine.getState().getId());
    }

    @Test
    public void whenLoadedEvent_thenStateIsLoaded() {
        stateMachine.sendEvent(DroneEvent.LOAD);
        stateMachine.sendEvent(DroneEvent.LOADED);
        assertEquals(DroneState.LOADED, stateMachine.getState().getId());
    }

    @Test
    public void whenDeliverEvent_thenStateIsDelivering() {
        stateMachine.sendEvent(DroneEvent.LOAD);
        stateMachine.sendEvent(DroneEvent.LOADED);
        stateMachine.sendEvent(DroneEvent.DELIVER);
        assertEquals(DroneState.DELIVERING, stateMachine.getState().getId());
    }

    @Test
    public void whenDeliveredEvent_thenStateIsDelivered() {
        stateMachine.sendEvent(DroneEvent.LOAD);
        stateMachine.sendEvent(DroneEvent.LOADED);
        stateMachine.sendEvent(DroneEvent.DELIVER);
        stateMachine.sendEvent(DroneEvent.DELIVERED);
        assertEquals(DroneState.DELIVERED, stateMachine.getState().getId());
    }

    @Test
    public void whenReturnEvent_thenStateIsReturning() {
        stateMachine.sendEvent(DroneEvent.LOAD);
        stateMachine.sendEvent(DroneEvent.LOADED);
        stateMachine.sendEvent(DroneEvent.DELIVER);
        stateMachine.sendEvent(DroneEvent.DELIVERED);
        stateMachine.sendEvent(DroneEvent.RETURN);
        assertEquals(DroneState.RETURNING, stateMachine.getState().getId());
    }

    @Test
    public void whenIdleEvent_thenStateIsIdle() {
        stateMachine.sendEvent(DroneEvent.LOAD);
        stateMachine.sendEvent(DroneEvent.LOADED);
        stateMachine.sendEvent(DroneEvent.DELIVER);
        stateMachine.sendEvent(DroneEvent.DELIVERED);
        stateMachine.sendEvent(DroneEvent.RETURN);
        stateMachine.sendEvent(DroneEvent.IDLE);
        assertEquals(DroneState.IDLE, stateMachine.getState().getId());
    }
}