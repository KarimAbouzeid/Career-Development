package com.example.demo.controller;

import com.example.demo.controllers.UserScoresController;
import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.services.UserScoresService;
import exceptions.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserScoresController.class)
@ContextConfiguration(classes = {UserScoresController.class, GlobalExceptionHandler.class})
public class UserScoresControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserScoresService userScoresService;


    private UserScoresDTO userScoresDTO;
    private UUID userScoreId;

    @BeforeEach
    public void setUp() {
        userScoreId = UUID.randomUUID();
        userScoresDTO = new UserScoresDTO();
        userScoresDTO.setScore(100);
        userScoresDTO.setUserId(userScoreId);

    }

    @Test
    public void getUserScore_userExists_ReturnsUserScoresDTO() throws Exception {
        when(userScoresService.getUserScore(userScoreId)).thenReturn(userScoresDTO);

        mockMvc.perform(get("/api/userScores/{id}", userScoreId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(100));
        verify(userScoresService, times(1)).getUserScore(userScoreId);
    }

    @Test
    public void getUserScore_userNotExists_ReturnsNotFound() throws Exception {
        when(userScoresService.getUserScore(userScoreId))
                .thenThrow(new EntityNotFoundException("User score not found"));

        mockMvc.perform(get("/api/userScores/{id}", userScoreId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userScoresService, times(1)).getUserScore(userScoreId);
    }

    @Test
    public void addUserScore_validInput_ReturnsCreated() throws Exception {
        when(userScoresService.addUserScore(any(UserScoresDTO.class))).thenReturn(userScoresDTO);

        mockMvc.perform(post("/api/userScores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\": 100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(100));
        verify(userScoresService, times(1)).addUserScore(any(UserScoresDTO.class));
    }

    @Test
    public void updateUserScore_userExists_ReturnsUpdatedUserScoresDTO() throws Exception {
        when(userScoresService.updateUserScore(any(UserScoresDTO.class))).thenReturn(userScoresDTO);

        mockMvc.perform(put("/api/userScores/{id}", userScoreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\": 150}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(100));
        verify(userScoresService, times(1)).updateUserScore(any(UserScoresDTO.class));
    }

    @Test
    public void updateUserScore_userNotExists_ReturnsNotFound() throws Exception {
        when(userScoresService.updateUserScore(any(UserScoresDTO.class)))
                .thenThrow(new EntityNotFoundException("User score not found"));

        mockMvc.perform(put("/api/userScores/{id}", userScoreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\": 150}"))
                .andExpect(status().isNotFound());

        verify(userScoresService, times(1)).updateUserScore(any(UserScoresDTO.class));
    }

    @Test
    public void deleteUserScore_userExists_ReturnsSuccessMessage() throws Exception {
        doNothing().when(userScoresService).deleteUserScore(userScoreId);

        mockMvc.perform(delete("/api/userScores/{id}", userScoreId))
                .andExpect(status().isOk())
                .andExpect(content().string("User score deleted successfully"));

        verify(userScoresService, times(1)).deleteUserScore(userScoreId);
    }

    @Test
    public void deleteUserScore_userNotExists_ReturnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("User score not found")).when(userScoresService).deleteUserScore(userScoreId);

        mockMvc.perform(delete("/api/userScores/{id}", userScoreId))
                .andExpect(status().isNotFound());

        verify(userScoresService, times(1)).deleteUserScore(userScoreId);
    }

    @Test
    public void getAllUserScores_validRequest_ReturnsUserScoresPage() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserScoresDTO> userScoresPage = new PageImpl<>(Collections.singletonList(userScoresDTO));
        when(userScoresService.getAllUserScores(pageable)).thenReturn(userScoresPage);

        mockMvc.perform(get("/api/userScores")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].score").value(100));

        verify(userScoresService, times(1)).getAllUserScores(pageable);
    }
}