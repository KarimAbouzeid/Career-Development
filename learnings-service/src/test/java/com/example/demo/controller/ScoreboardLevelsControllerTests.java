package com.example.demo.controller;


import com.example.demo.controllers.ScoreboardLevelsController;
import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.services.ScoreboardLevelsService;
import exceptions.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScoreboardLevelsController.class)
@ContextConfiguration(classes = {ScoreboardLevelsController.class, GlobalExceptionHandler.class})
public class ScoreboardLevelsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreboardLevelsService scoreboardLevelsService;

    private ScoreboardLevelsDTO scoreboardLevelsDTO;

    @BeforeEach
    public void setUp() {
        scoreboardLevelsDTO = new ScoreboardLevelsDTO("Expert", 100);
    }

    @Test
    public void getScoreboard_scoreboardLevelExists_ReturnsScoreboardDTO() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        when(scoreboardLevelsService.getScoreboardLevel(scoreboardLevelId)).thenReturn(scoreboardLevelsDTO);

        mockMvc.perform(get("/api/scoreboardLevels/{id}", scoreboardLevelId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.levelName").value("Expert"))
                .andExpect(jsonPath("$.minScore").value(100));


        verify(scoreboardLevelsService, times(1)).getScoreboardLevel(scoreboardLevelId);
    }

    @Test
    void getScoreboard_scoreboardLevelNotExists_returnsNotFound() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();
        when(scoreboardLevelsService.getScoreboardLevel(scoreboardLevelId)).thenThrow(new EntityNotFoundException("ScoreboardLevel Level with id " + scoreboardLevelId + " not found"));

        mockMvc.perform(get("/api/scoreboardLevels/{id}", scoreboardLevelId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ScoreboardLevel Level with id " + scoreboardLevelId + " not found"));
    }

    @Test
    void getScoreboard_unexpectedError_returnsInternalServerError() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();
        when(scoreboardLevelsService.getScoreboardLevel(scoreboardLevelId)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/scoreboardLevels/{id}", scoreboardLevelId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void addScoreboard_scoreboardLevelDetailsEntered_ReturnsScoreboardDTO() throws Exception {
        when(scoreboardLevelsService.addScoreboardLevel(any(ScoreboardLevelsDTO.class))).thenReturn(scoreboardLevelsDTO);

        mockMvc.perform(post("/api/scoreboardLevels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"levelName\": \"Expert\"}")
                        .content("{\"minScore\": 100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.levelName").value("Expert"))
                .andExpect(jsonPath("$.minScore").value(100));

        verify(scoreboardLevelsService, times(1)).addScoreboardLevel(any(ScoreboardLevelsDTO.class));
    }

    @Test
    void addScoreboard_unexpectedError_returnsInternalServerError() throws Exception {
        when(scoreboardLevelsService.addScoreboardLevel(any(ScoreboardLevelsDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/scoreboardLevels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"levelName\": \"Expert\"}")
                        .content("{\"minScore\": 100}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void updateScoreboard_scoreboardLevelExists_ReturnsScoreboardDTO() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        when(scoreboardLevelsService.updateScoreboardLevel(eq(scoreboardLevelId), any(ScoreboardLevelsDTO.class))).thenReturn(scoreboardLevelsDTO);

        mockMvc.perform(put("/api/scoreboardLevels/{id}", scoreboardLevelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"levelName\": \"Professional\"}")
                        .content("{\"minScore\": 120}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.levelName").value("Expert"));

        verify(scoreboardLevelsService, times(1)).updateScoreboardLevel(eq(scoreboardLevelId), any(ScoreboardLevelsDTO.class));
    }

    @Test
    public void updateScoreboard_scoreboardLevelNotExists_ReturnsScoreboardDTO() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        when(scoreboardLevelsService.updateScoreboardLevel(eq(scoreboardLevelId), any(ScoreboardLevelsDTO.class))).thenThrow(new EntityNotFoundException("ScoreboardLevel with id " + scoreboardLevelId + " not found"));

        mockMvc.perform(put("/api/scoreboardLevels/{id}", scoreboardLevelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"levelName\": \"Professional\"}")
                        .content("{\"minScore\": 120}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ScoreboardLevel with id " + scoreboardLevelId + " not found"));
    }

    @Test
    void updateScoreboard_unexpectedError_returnsInternalServerError() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        when(scoreboardLevelsService.updateScoreboardLevel(eq(scoreboardLevelId), any(ScoreboardLevelsDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/scoreboardLevels/{id}", scoreboardLevelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"levelName\": \"Professional\"}")
                        .content("{\"minScore\": 120}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void deleteScoreboardLevel_scoreboardLevelExists_ReturnsSuccessMessage() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        doNothing().when(scoreboardLevelsService).deletescoreboardLevel(scoreboardLevelId);

        mockMvc.perform(delete("/api/scoreboardLevels/{id}", scoreboardLevelId))
                .andExpect(status().isOk())
                .andExpect(content().string("ScoreboardLevel deleted successfully"));

        verify(scoreboardLevelsService, times(1)).deletescoreboardLevel(scoreboardLevelId);
    }

    @Test
    public void deleteScoreboard_scoreboardLevelNotExists_ReturnsScoreboardDTO() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        doThrow(new EntityNotFoundException("ScoreboardLevel with id " + scoreboardLevelId + " not found"))
                .when(scoreboardLevelsService).deletescoreboardLevel(scoreboardLevelId);


        mockMvc.perform(delete("/api/scoreboardLevels/{id}", scoreboardLevelId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ScoreboardLevel with id " + scoreboardLevelId + " not found"));
    }

    @Test
    void deleteScoreboard_unexpectedError_returnsInternalServerError() throws Exception {
        UUID scoreboardLevelId = UUID.randomUUID();

        doThrow(new RuntimeException())
                .when(scoreboardLevelsService).deletescoreboardLevel(scoreboardLevelId);


        mockMvc.perform(delete("/api/scoreboardLevels/{id}", scoreboardLevelId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

}
