package com.example.demo.controller;

import com.example.demo.controllers.UserLearningsController;
import com.example.demo.dtos.SubmitUserLearningDTO;
import com.example.demo.dtos.UserLearningsDTO;
import com.example.demo.services.UserLearningsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserLearningsController.class)
@ContextConfiguration(classes = {UserLearningsController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
public class UserLearningsControllerTest {

    @MockBean
    private UserLearningsService userLearningsService;

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSubmitUserLearning_Success() throws Exception {
        SubmitUserLearningDTO dto = new SubmitUserLearningDTO();

        doNothing().when(userLearningsService).submitUserLearning(any(SubmitUserLearningDTO.class));

        mockMvc.perform(post("/api/userLearnings/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Learning submitted successfully"));

        verify(userLearningsService, times(1)).submitUserLearning(any(SubmitUserLearningDTO.class));
    }

    @Test
    public void testGetSubmittedLearningsByUser_Success() throws Exception {
        UUID userId = UUID.randomUUID();
        List<UserLearningsDTO> learningsDTOList = new ArrayList<>();

        when(userLearningsService.getSubmittedLearningsByUser(userId)).thenReturn(learningsDTOList);

        mockMvc.perform(get("/api/userLearnings/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(learningsDTOList)));

        verify(userLearningsService, times(1)).getSubmittedLearningsByUser(userId);
    }

    @Test
    public void testGetSubmittedLearningById_Success() throws Exception {
        UUID learningId = UUID.randomUUID();
        UserLearningsDTO learningsDTO = new UserLearningsDTO();
        
        when(userLearningsService.getSubmittedLearningById(learningId)).thenReturn(learningsDTO);

        mockMvc.perform(get("/api/userLearnings/{id}", learningId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(learningsDTO)));

        verify(userLearningsService, times(1)).getSubmittedLearningById(learningId);
    }

    @Test
    public void testUpdateApprovalStatus_Success() throws Exception {
        UUID learningId = UUID.randomUUID();
        String newStatus = "Approved";

        doNothing().when(userLearningsService).updateApprovalStatus(learningId, newStatus);

        mockMvc.perform(put("/api/userLearnings/updateApprovalStatus/{id}", learningId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newStatus))
                .andExpect(status().isOk())
                .andExpect(content().string("Approval status updated successfully"));

        verify(userLearningsService, times(1)).updateApprovalStatus(learningId, newStatus);
    }

    @Test
    public void testUpdateApprovalStatus_LearningNotFound_throwsException() throws Exception {
        UUID learningId = UUID.randomUUID();
        String newStatus = "Approved";

        doThrow(new EntityNotFoundException("Learning not found with id " + learningId))
                .when(userLearningsService).updateApprovalStatus(learningId, newStatus);

        mockMvc.perform(put("/api/userLearnings/updateApprovalStatus/{id}", learningId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newStatus))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).updateApprovalStatus(learningId, newStatus);
    }

    @Test
    public void testGetSubmittedLearningById_LearningNotFound_throwsException() throws Exception {
        UUID learningId = UUID.randomUUID();

        // Mock the service to throw an exception
        when(userLearningsService.getSubmittedLearningById(learningId))
                .thenThrow(new EntityNotFoundException("Learning not found with id " + learningId));

        mockMvc.perform(get("/api/userLearnings/{id}", learningId))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).getSubmittedLearningById(learningId);
    }


    @Test
    public void testUpdateComment_Success() throws Exception {
        UUID learningId = UUID.randomUUID();
        String newComment = "This is an updated comment";

        doNothing().when(userLearningsService).updateComment(learningId, newComment);

        mockMvc.perform(put("/api/userLearnings/updateComment/{id}", learningId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment updated successfully"));

        verify(userLearningsService, times(1)).updateComment(learningId, newComment);
    }

    @Test
    public void testUpdateComment_LearningNotFound_throwsException() throws Exception {
        UUID learningId = UUID.randomUUID();
        String newComment = "This is an updated comment";

        doThrow(new EntityNotFoundException("Learning not found with id " + learningId))
                .when(userLearningsService).updateComment(learningId, newComment);

        mockMvc.perform(put("/api/userLearnings/updateComment/{id}", learningId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newComment))
                .andExpect(status().isNotFound());

        verify(userLearningsService, times(1)).updateComment(learningId, newComment);
    }




}
