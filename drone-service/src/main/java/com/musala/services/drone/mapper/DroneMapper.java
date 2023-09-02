package com.musala.services.drone.mapper;

import com.musala.services.drone.model.dto.DroneDTO;
import com.musala.services.drone.model.entity.Drone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    DroneDTO toDTO(Drone drone);

    Drone toEntity(DroneDTO droneDTO);

    List<DroneDTO> toDTOList(List<Drone> drones);
}