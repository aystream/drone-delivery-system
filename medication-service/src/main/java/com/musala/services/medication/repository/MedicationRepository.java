package com.musala.services.medication.repository;

import com.musala.services.medication.model.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String>, JpaSpecificationExecutor<Medication> {
    List<Medication> findByCodeIn(List<String> codes);

    List<Medication> findByDroneSerialNumber(String droneSerial);
}