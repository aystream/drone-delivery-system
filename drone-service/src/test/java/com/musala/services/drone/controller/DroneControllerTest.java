package com.musala.services.drone.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.services.drone.model.dto.DroneDTO;
import com.musala.services.drone.model.enums.DroneEvent;
import com.musala.services.drone.model.enums.DroneModel;
import com.musala.services.drone.model.enums.DroneState;
import com.musala.services.drone.service.DroneService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @Test
    public void testCreateDrone() throws Exception {
        DroneDTO drone = new DroneDTO().setSerialNumber("67890");
        when(droneService.registerDrone(any())).thenReturn(drone);

        mockMvc.perform(post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(drone)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serialNumber").value("67890"));
    }

    @Test
    public void testGetAllDronesWithSearchParams() throws Exception {
        DroneDTO drone1 = new DroneDTO().setSerialNumber("12345").setModel(DroneModel.MIDDLEWEIGHT);
        DroneDTO drone2 = new DroneDTO().setSerialNumber("67890").setModel(DroneModel.MIDDLEWEIGHT);

        List<DroneDTO> drones = Arrays.asList(drone1, drone2);
        Page<DroneDTO> dronePage = new PageImpl<>(drones, PageRequest.of(0, 10), drones.size());
        Map<String, String> searchParams = Collections.singletonMap("model", "MIDDLEWEIGHT");

        when(droneService.getAllDrones(searchParams, PageRequest.of(0, 10)))
                .thenReturn(dronePage);

        mockMvc.perform(get("/api/v1/drones")
                        .param("page", "0")
                        .param("size", "10")
                        .param("model", "MIDDLEWEIGHT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDroneBySerialNumber() throws Exception {
        DroneDTO drone = new DroneDTO().setSerialNumber("12345");
        when(droneService.getDrone("12345")).thenReturn(drone);

        mockMvc.perform(get("/api/v1/drones/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("12345"));
    }

    @Test
    public void testUpdateDroneEvent() throws Exception {
        String serialNumber = "12345";
        DroneEvent newEvent = DroneEvent.LOADED;

        when(droneService.changeDroneEvent(serialNumber, newEvent))
                .thenReturn(new DroneDTO().setSerialNumber(serialNumber).setState(DroneState.LOADED));

        mockMvc.perform(put("/api/v1/drones/" + serialNumber + "/event")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(newEvent)))
                .andExpect(status().isOk());
    }
}