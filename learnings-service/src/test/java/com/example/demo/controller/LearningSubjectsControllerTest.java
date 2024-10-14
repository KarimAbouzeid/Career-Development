package com.example.demo.controller;

import com.example.demo.controllers.LearningSubjectsController;
import com.example.demo.dtos.LearningSubjectsDTO;
import com.example.demo.enums.SubjectType;
import com.example.demo.services.LearningSubjectsService;
import exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LearningSubjectsController.class)
@ContextConfiguration(classes = {LearningSubjectsController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
public class LearningSubjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LearningSubjectsService learningSubjectsService;


    private LearningSubjectsDTO learningSubjectsDTO;

    @BeforeEach
    public void setUp() {
        UUID learningSubjectId = UUID.randomUUID();
        learningSubjectsDTO = new LearningSubjectsDTO(learningSubjectId,SubjectType.FUNCTIONAL ,"Math");
    }

    @Test
    public void getLearningSubjectById_ExistingId_ReturnsLearningSubjectDTO() throws Exception {
        UUID learningSubjectId = learningSubjectsDTO.getId();
        when(learningSubjectsService.getLearningSubjectById(learningSubjectId)).thenReturn(learningSubjectsDTO);

        mockMvc.perform(get("/api/learningSubjects/{id}", learningSubjectId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + learningSubjectId + "\",\"subject\":\"Math\"}"));

        verify(learningSubjectsService, times(1)).getLearningSubjectById(learningSubjectId);
    }

    @Test
    public void getAllLearningSubjects_ReturnsListOfLearningSubjects() throws Exception {
        when(learningSubjectsService.getLearningSubjects()).thenReturn(Collections.singletonList(learningSubjectsDTO));

        mockMvc.perform(get("/api/learningSubjects"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"" + learningSubjectsDTO.getId() + "\",\"type\":\"FUNCTIONAL\",\"subject\":\"Math\"}]"));

        verify(learningSubjectsService, times(1)).getLearningSubjects();
    }

    @Test
    public void addLearningSubject_ValidDTO_ReturnsSuccessMessage() throws Exception {
        String json = "{\"id\":\"" + learningSubjectsDTO.getId() + "\",\"type\":\"FUNCTIONAL\",\"subject\":\"Math\"}";

        mockMvc.perform(post("/api/learningSubjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Learning subject added successfully"));

        verify(learningSubjectsService, times(1)).addLearningSubject(any(LearningSubjectsDTO.class));
    }

    @Test
    public void updateLearningSubject_ExistingId_ReturnsSuccessMessage() throws Exception {
        UUID learningSubjectId = learningSubjectsDTO.getId();
        String json = "{\"id\":\"" + learningSubjectId + "\",\"type\":\"FUNCTIONAL\",\"subject\":\"Math\"}";

        mockMvc.perform(put("/api/learningSubjects/{id}", learningSubjectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Learning subject updated successfully"));

        verify(learningSubjectsService, times(1)).updateLearningSubject(eq(learningSubjectId), any(LearningSubjectsDTO.class));
    }

    @Test
    public void deleteLearningSubject_ExistingId_ReturnsSuccessMessage() throws Exception {
        UUID learningSubjectId = learningSubjectsDTO.getId();

        mockMvc.perform(delete("/api/learningSubjects/{id}", learningSubjectId))
                .andExpect(status().isOk())
                .andExpect(content().string("Learning subject deleted successfully"));

        verify(learningSubjectsService, times(1)).deleteLearningSubject(learningSubjectId);
    }
}
