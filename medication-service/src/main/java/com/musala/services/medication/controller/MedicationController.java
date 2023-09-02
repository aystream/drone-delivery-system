package com.musala.services.medication.controller;

import com.musala.services.medication.model.dto.MedicationDTO;
import com.musala.services.medication.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/medications")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping
    @Operation(summary = "Register a new medication")
    public ResponseEntity<MedicationDTO> registerMedication(@Valid @RequestBody MedicationDTO medicationDto) {
        MedicationDTO savedMedication = medicationService.registerMedication(medicationDto);
        return new ResponseEntity<>(savedMedication, HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    @Operation(summary = "Get a medication by its code")
    public ResponseEntity<MedicationDTO> getMedication(@PathVariable String code) {
        MedicationDTO medication = medicationService.getMedication(code);
        return ResponseEntity.ok(medication);
    }

    @GetMapping
    @Operation(summary = "Get all medications with optional filtering and searching")
    public ResponseEntity<Page<MedicationDTO>> getAllMedications(
            @ParameterObject Pageable pageable,
            @RequestParam Map<String, String> searchParams) {
        Page<MedicationDTO> medications = medicationService.getAllMedications(pageable, searchParams);
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/drone/{serialNumber}")
    @Operation(summary = "Get medications assigned to a specific drone")
    public ResponseEntity<List<MedicationDTO>> getMedicationsByDroneSerial(@PathVariable String serialNumber) {
        List<MedicationDTO> medications = medicationService.getMedicationsByDroneSerial(serialNumber);
        return ResponseEntity.ok(medications);
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "Delete a medication by its code")
    public ResponseEntity<String> deleteMedication(@PathVariable String code) {
        medicationService.deleteMedication(code);
        return ResponseEntity.ok("Medication deleted successfully.");
    }

    @GetMapping("/codes")
    @Operation(summary = "Get medications by their codes")
    public List<MedicationDTO> getMedicationsByCodes(@RequestParam List<String> codes) {
        return medicationService.findByCodes(codes);
    }

    @PutMapping("/codes")
    @Operation(summary = "Assign medications to a drone")
    public List<MedicationDTO> setMedicationsByDroneSerialNumber(@RequestBody List<String> codes,
                                                                 @RequestParam String droneSerialNumber) {
        return medicationService.assignMedicationsToDrone(codes, droneSerialNumber);
    }
}
