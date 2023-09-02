package com.musala.services.medication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.services.medication.model.dto.MedicationDTO;
import com.musala.services.medication.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(MedicationController.class)
public class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationService medicationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterMedication() throws Exception {
        MedicationDTO medicationDto = new MedicationDTO().setCode("123").setName("Medicine");

        when(medicationService.registerMedication(medicationDto)).thenReturn(medicationDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetMedication() throws Exception {
        MedicationDTO medicationDto = new MedicationDTO().setCode("123").setName("Medicine");

        when(medicationService.getMedication("123")).thenReturn(medicationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medications/{code}", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Medicine"));
    }

    @Test
    public void testGetAllMedications() throws Exception {
        MedicationDTO medicationDto = new MedicationDTO().setCode("123").setName("Medicine");
        Page<MedicationDTO> medicationPage = new PageImpl<>(Collections.singletonList(medicationDto));

        when(medicationService.getAllMedications(PageRequest.of(0, 20), Collections.emptyMap()))
                .thenReturn(medicationPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medications"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].code").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Medicine"));
    }

    @Test
    public void testDeleteMedication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/medications/{code}", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Medication deleted successfully."));

        verify(medicationService).deleteMedication("123");
    }

    @Test
    public void testGetMedicationsByDroneSerial() throws Exception {
        MedicationDTO medicationDto1 = new MedicationDTO().setCode("123").setName("Medicine 1");
        MedicationDTO medicationDto2 = new MedicationDTO().setCode("456").setName("Medicine 2");
        List<MedicationDTO> medications = Arrays.asList(medicationDto1, medicationDto2);

        when(medicationService.getMedicationsByDroneSerial("serial123")).thenReturn(medications);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medications/drone/{serialNumber}", "serial123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Medicine 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].code").value("456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Medicine 2"));
    }

    @Test
    public void testGetMedicationsByCodes() throws Exception {
        MedicationDTO medicationDto1 = new MedicationDTO().setCode("123").setName("Medicine 1");
        MedicationDTO medicationDto2 = new MedicationDTO().setCode("456").setName("Medicine 2");
        List<MedicationDTO> medications = Arrays.asList(medicationDto1, medicationDto2);

        List<String> codes = Arrays.asList("123", "456");
        when(medicationService.findByCodes(codes)).thenReturn(medications);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/medications/codes")
                        .param("codes", "123", "456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Medicine 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].code").value("456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Medicine 2"));
    }

    @Test
    public void testSetMedicationsByDroneSerialNumber() throws Exception {
        List<String> codes = Arrays.asList("123", "456");
        String droneSerialNumber = "serial123";

        MedicationDTO medicationDto1 = new MedicationDTO().setCode("123").setName("Medicine 1");
        MedicationDTO medicationDto2 = new MedicationDTO().setCode("456").setName("Medicine 2");
        List<MedicationDTO> updatedMedications = Arrays.asList(medicationDto1, medicationDto2);

        when(medicationService.assignMedicationsToDrone(codes, droneSerialNumber))
                .thenReturn(updatedMedications);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/medications/codes")
                        .param("droneSerialNumber", droneSerialNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(codes)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Medicine 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].code").value("456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Medicine 2"));
    }
}