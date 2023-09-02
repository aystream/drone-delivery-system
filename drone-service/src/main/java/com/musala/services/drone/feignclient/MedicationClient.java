package com.musala.services.drone.feignclient;

import com.musala.services.drone.model.dto.MedicationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "medication-service", path = "/api/v1/medications")
public interface MedicationClient {

    @GetMapping("/codes")
    List<MedicationDTO> getMedicationsByCodes(@RequestParam List<String> codes);

    @PutMapping("/codes")
    List<MedicationDTO> setMedicationsByDroneSerialNumber(@RequestBody List<String> codes, @RequestParam String droneSerialNumber);
}