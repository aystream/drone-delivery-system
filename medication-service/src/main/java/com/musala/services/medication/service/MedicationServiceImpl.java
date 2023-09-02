package com.musala.services.medication.service;

import com.musala.services.medication.mapper.MedicationMapper;
import com.musala.services.medication.model.dto.MedicationDTO;
import com.musala.services.medication.model.entity.Medication;
import com.musala.services.medication.repository.MedicationRepository;
import com.musala.services.medication.repository.MedicationSpecification;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public MedicationDTO registerMedication(MedicationDTO medicationDto) {
        Medication medication = medicationMapper.toEntity(medicationDto);
        Medication savedMedication = medicationRepository.save(medication);
        return medicationMapper.toDTO(savedMedication);
    }

    @Override
    public MedicationDTO getMedication(String code) {
        Medication medication = medicationRepository.findById(code)
                .orElseThrow(() -> new NotFoundException("Medication with code " + code + " not found"));
        return medicationMapper.toDTO(medication);
    }

    @Override
    public Page<MedicationDTO> getAllMedications(Pageable pageable, Map<String, String> searchParams) {
        Page<Medication> medications = medicationRepository.findAll(
                MedicationSpecification.searchAndFilter(searchParams),
                pageable
        );
        return medications.map(medicationMapper::toDTO);
    }

    @Override
    public List<MedicationDTO> getMedicationsByDroneSerial(String serialNumber) {
        List<Medication> medications = medicationRepository.findByDroneSerialNumber(serialNumber);
        return medications.stream().map(medicationMapper::toDTO).collect(Collectors.toList());
    }

    // TODO change to soft delete, because we don't want to delete medications
    @Override
    public void deleteMedication(String code) {
        medicationRepository.deleteById(code);
    }

    @Override
    public List<MedicationDTO> findByCodes(List<String> codes) {
        List<Medication> medications = medicationRepository.findByCodeIn(codes);
        return medications.stream()
                .map(medicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicationDTO> assignMedicationsToDrone(List<String> codes, String droneSerialNumber) {
        List<Medication> medications = medicationRepository.findByCodeIn(codes);

        medications.forEach(medication -> medication.setDroneSerialNumber(droneSerialNumber));

        List<Medication> updatedMedications = medicationRepository.saveAll(medications);
        return updatedMedications.stream()
                .map(medicationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
