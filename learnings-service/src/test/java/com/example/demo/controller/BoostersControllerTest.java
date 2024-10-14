package com.example.demo.controller;

import com.example.demo.controllers.BoostersController;
import com.example.demo.dtos.BoostersDTO;
import com.example.demo.services.BoostersService;
import exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoostersController.class)
@ContextConfiguration(classes = {BoostersController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
class BoostersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoostersService boostersService;

    private BoostersDTO boostersDTO;

    @BeforeEach
    void setUp() {
        boostersDTO = new BoostersDTO();
        boostersDTO.setId(UUID.randomUUID());
        boostersDTO.setActive(false);
    }

    @Test
    void createBooster_shouldReturnSuccessMessage() throws Exception {

        mockMvc.perform(post("/api/boosters/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Booster\", \"type\": \"Type A\", \"value\": 100,  \"isActive\": \"False\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booster created successfully."));

        verify(boostersService).createBooster(any(BoostersDTO.class));

    }

    @Test
    void getBoosterById_shouldReturnBooster() throws Exception {
        UUID id = boostersDTO.getId();
        when(boostersService.getBoosterById(id)).thenReturn(boostersDTO);

        mockMvc.perform(get("/api/boosters/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.active").value(boostersDTO.isActive()));
    }

    @Test
    void getAllBoosters_shouldReturnListOfBoosters() throws Exception {
        List<BoostersDTO> boostersList = new ArrayList<>();
        boostersList.add(boostersDTO);
        when(boostersService.getAllBoosters()).thenReturn(boostersList);

        mockMvc.perform(get("/api/boosters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(boostersDTO.getId().toString()));
    }

    @Test
    void activateBooster_shouldReturnSuccessMessage() throws Exception {
        UUID id = boostersDTO.getId();
        when(boostersService.activateBooster(id)).thenReturn("Booster activated successfully.");

        mockMvc.perform(put("/api/boosters/activate/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Booster activated successfully."));
    }

    @Test
    void deactivateBooster_shouldReturnSuccessMessage() throws Exception {
        UUID id = boostersDTO.getId();
        when(boostersService.deactivateBooster(id)).thenReturn("Booster deactivated successfully.");

        mockMvc.perform(put("/api/boosters/deactivate/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Booster deactivated successfully."));
    }
}
