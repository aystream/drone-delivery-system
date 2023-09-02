package com.musala.services.drone.repository;

import com.musala.services.drone.model.entity.Drone;
import com.musala.services.drone.model.enums.DroneState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long>, JpaSpecificationExecutor<Drone> {
    Optional<Drone> findBySerialNumber(String serialNumber);

    Page<Drone> findByStateAndBatteryCapacityGreaterThan(DroneState state, Double batteryCapacity, Pageable pageable);
}
