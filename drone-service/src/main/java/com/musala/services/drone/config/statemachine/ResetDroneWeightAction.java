package com.musala.services.drone.config.statemachine;

import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.model.enums.DroneState;
import com.musala.services.drone.repository.DroneRepository;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class ResetDroneWeightAction implements Action<DroneState, DroneEvent> {

    private final DroneRepository droneRepository;

    public ResetDroneWeightAction(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public void execute(StateContext<DroneState, DroneEvent> context) {
        Drone drone = (Drone) context.getExtendedState().getVariables().get("currentDrone");
        if (drone != null) {
            drone.setCurrentWeight(0.0);
            droneRepository.save(drone);
        }
    }
}
