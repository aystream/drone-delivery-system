package com.musala.services.medication.mapper;

import com.musala.services.medication.model.dto.MedicationDTO;
import com.musala.services.medication.model.entity.Medication;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    MedicationDTO toDTO(Medication medication);

    Medication toEntity(MedicationDTO medicationDTO);

    List<MedicationDTO> toDTOList(List<Medication> medications);

    List<Medication> toEntityList(List<MedicationDTO> medicationDTOs);
}