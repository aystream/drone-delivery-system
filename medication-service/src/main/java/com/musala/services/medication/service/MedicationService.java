package com.musala.services.medication.service;

import com.musala.services.medication.model.dto.MedicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MedicationService {
    MedicationDTO registerMedication(MedicationDTO medicationDto);

    MedicationDTO getMedication(String code);

    Page<MedicationDTO> getAllMedications(Pageable pageable, Map<String, String> searchParams);

    List<MedicationDTO> getMedicationsByDroneSerial(String serialNumber);

    void deleteMedication(String code);

    List<MedicationDTO> findByCodes(List<String> codes);

    List<MedicationDTO> assignMedicationsToDrone(List<String> codes, String droneSerialNumber);
}
