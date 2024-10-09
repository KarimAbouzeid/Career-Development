package com.example.demo.controller;

import com.example.demo.controllers.LearningsController;
import com.example.demo.controllers.ScoreboardLevelsController;
import com.example.demo.dtos.LearningsDTO;
import com.example.demo.services.LearningsService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningsController.class)
@ContextConfiguration(classes = {LearningsController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
public class LearningsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LearningsService learningsService;

    @Autowired
    private ObjectMapper objectMapper;

    private LearningsDTO learningsDTO;
    private UUID id;

    @BeforeEach
    public void setup() {
        id = UUID.randomUUID();
        learningsDTO = new LearningsDTO();
        learningsDTO.setId(id);
    }

    @Test
    public void getAllLearnings_returnsListOfLearnings() throws Exception {
        when(learningsService.getAllLearnings()).thenReturn(Collections.singletonList(learningsDTO));

        mockMvc.perform(get("/api/learnings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()));

        verify(learningsService, times(1)).getAllLearnings();
    }

    @Test
    public void getLearningById_learningExists_returnsLearning() throws Exception {
        when(learningsService.getLearningById(id)).thenReturn(learningsDTO);

        mockMvc.perform(get("/api/learnings/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        verify(learningsService, times(1)).getLearningById(id);
    }

    @Test
    public void addLearning_validLearning_returnsCreatedStatus() throws Exception {
        mockMvc.perform(post("/api/learnings/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learningsDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Learning added successfully"));

        verify(learningsService, times(1)).addLearning(any(LearningsDTO.class));
    }

    @Test
    public void updateLearning_validLearning_returnsOkStatus() throws Exception {
        mockMvc.perform(put("/api/learnings/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learningsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Learning updated successfully"));

        verify(learningsService, times(1)).updateLearning(id, learningsDTO);
    }
}
