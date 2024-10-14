package com.example.demo.controller;

import com.example.demo.controllers.LearningTypesController;
import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.services.LearningTypesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningTypesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LearningTypesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LearningTypesService learningTypesService;

    private List<LearningTypesDTO> learningTypesList;
    private LearningTypesDTO learningTypeDTO;

    @BeforeEach
    public void setUp() {
        learningTypesList = new ArrayList<>();
        learningTypesList.add(new LearningTypesDTO(UUID.randomUUID(), "Blog", 5));
        learningTypesList.add(new LearningTypesDTO(UUID.randomUUID(), "Video", 5));

        learningTypeDTO = new LearningTypesDTO(UUID.randomUUID(), "Course", 15);
    }

    @Test
    public void getAllLearningTypes_ReturnsList_Success() throws Exception {
        when(learningTypesService.getLearningTypes()).thenReturn(learningTypesList);

        mockMvc.perform(get("/api/learningTypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].typeName").value("Blog"))
                .andExpect(jsonPath("$[1].typeName").value("Video"));

        verify(learningTypesService, times(1)).getLearningTypes();
    }

    @Test
    public void getLearningTypeById_ReturnsLearningType_Success() throws Exception {
        UUID learningTypeId = learningTypeDTO.getId();
        when(learningTypesService.getLearningTypeById(learningTypeId)).thenReturn(learningTypeDTO);

        mockMvc.perform(get("/api/learningTypes/{id}", learningTypeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.typeName").value("Course"));

        verify(learningTypesService, times(1)).getLearningTypeById(learningTypeId);
    }

    @Test
    public void addLearningType_Success() throws Exception {

        when(learningTypesService.addLearningType(any(LearningTypesDTO.class)))
                .thenReturn(learningTypeDTO);

        mockMvc.perform(post("/api/learningTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"typeName\": \"New Type\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("learning type added successfully"));

        verify(learningTypesService, times(1)).addLearningType(any(LearningTypesDTO.class));
    }

    @Test
    public void updateLearningType_Success() throws Exception {
        UUID learningTypeId = UUID.randomUUID();
        LearningTypesDTO updatedLearningType = new LearningTypesDTO(learningTypeId, "Updated Type",5);

        when(learningTypesService.updateLearningType(eq(learningTypeId), any(LearningTypesDTO.class)))
                .thenReturn(updatedLearningType);

        mockMvc.perform(put("/api/learningTypes/{id}", learningTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"typeName\": \"Updated Type\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("learning type updated successfully"));

        verify(learningTypesService, times(1)).updateLearningType(eq(learningTypeId), any(LearningTypesDTO.class));
    }

    @Test
    public void deleteLearningType_Success() throws Exception {
        UUID learningTypeId = UUID.randomUUID();
        doNothing().when(learningTypesService).deleteLearningType(learningTypeId);

        mockMvc.perform(delete("/api/learningTypes/{id}", learningTypeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Learning type deleted successfully"));

        verify(learningTypesService, times(1)).deleteLearningType(learningTypeId);
    }

}