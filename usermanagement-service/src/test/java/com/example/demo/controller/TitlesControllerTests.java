package com.example.demo.controller;


import com.example.demo.controllers.TitlesController;
import com.example.demo.dtos.TitlesDTO;
import com.example.demo.services.TitlesServices;
import exceptions.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(TitlesController.class)
@ContextConfiguration(classes = {TitlesController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)

public class TitlesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TitlesServices titlesServices;

    private TitlesDTO titlesDTO;

    @BeforeEach
    public void setUp() {
        titlesDTO = new TitlesDTO();
        titlesDTO.setTitle("Software Engineer");
        titlesDTO.setIsManager(true);
    }

    @Test
    public void getTitle_titleExists_ReturnsTitlesDTO() throws Exception {
        UUID titleId = UUID.randomUUID();

        when(titlesServices.getTitles(titleId)).thenReturn(titlesDTO);

        mockMvc.perform(get("/api/titles/{id}", titleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.isManager").value(true));

        verify(titlesServices, times(1)).getTitles(titleId);
    }

    @Test
    void getTitle_titleNotExists_ReturnsNotFound() throws Exception {
        UUID titleId = UUID.randomUUID();
        when(titlesServices.getTitles(titleId)).thenThrow(new EntityNotFoundException("Title with id " + titleId + " not found"));

        mockMvc.perform(get("/api/titles/{id}", titleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Title with id " + titleId + " not found"));
    }

    @Test
    void getTitle_unexpectedError_returnsInternalServerError() throws Exception {
        UUID titleId = UUID.randomUUID();
        when(titlesServices.getTitles(titleId)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/titles/{id}", titleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void addTitle_titleDetailsEntered_ReturnsTitlesDTO() throws Exception {
        when(titlesServices.addTitles(any(TitlesDTO.class))).thenReturn(titlesDTO);

        mockMvc.perform(post("/api/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Software Engineer\", \"isManager\": true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.isManager").value(true));

        verify(titlesServices, times(1)).addTitles(any(TitlesDTO.class));
    }

    @Test
    void addTitle_unexpectedError_returnsInternalServerError() throws Exception {
        when(titlesServices.addTitles(any(TitlesDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Software Engineer\", \"isManager\": true}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void updateTitle_titleExists_ReturnsTitlesDTO() throws Exception {
        UUID titleId = UUID.randomUUID();

        when(titlesServices.updateTitles(eq(titleId), any(TitlesDTO.class))).thenReturn(titlesDTO);

        mockMvc.perform(put("/api/titles/{id}", titleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"isManager\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Software Engineer"))
                .andExpect(jsonPath("$.isManager").value(true));

        verify(titlesServices, times(1)).updateTitles(eq(titleId), any(TitlesDTO.class));
    }

    @Test
    public void updateTitle_titleNotExists_ReturnsNotFound() throws Exception {
        UUID titleId = UUID.randomUUID();

        when(titlesServices.updateTitles(eq(titleId), any(TitlesDTO.class))).thenThrow(new EntityNotFoundException("Title with id " + titleId + " not found"));

        mockMvc.perform(put("/api/titles/{id}", titleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"isManager\": false}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Title with id " + titleId + " not found"));
    }

    @Test
    void updateTitle_unexpectedError_returnsInternalServerError() throws Exception {
        UUID titleId = UUID.randomUUID();

        when(titlesServices.updateTitles(eq(titleId), any(TitlesDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/titles/{id}", titleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"isManager\": false}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void deleteTitle_titleExists_ReturnsSuccessMessage() throws Exception {
        UUID titleId = UUID.randomUUID();

        doNothing().when(titlesServices).deleteTitles(titleId);

        mockMvc.perform(delete("/api/titles/{id}", titleId))
                .andExpect(status().isOk())
                .andExpect(content().string("Title deleted successfully"));

        verify(titlesServices, times(1)).deleteTitles(titleId);
    }

    @Test
    public void deleteTitle_titleNotExists_ReturnsNotFound() throws Exception {
        UUID titleId = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Title with id " + titleId + " not found"))
                .when(titlesServices).deleteTitles(titleId);

        mockMvc.perform(delete("/api/titles/{id}", titleId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Title with id " + titleId + " not found"));
    }

    @Test
    void deleteTitle_unexpectedError_returnsInternalServerError() throws Exception {
        UUID titleId = UUID.randomUUID();

        doThrow(new RuntimeException()).when(titlesServices).deleteTitles(titleId);

        mockMvc.perform(delete("/api/titles/{id}", titleId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void getTitlesByDepartment_success_ReturnsTitlesList() throws Exception {
        UUID departmentId = UUID.randomUUID();
        List<TitlesDTO> titles = new ArrayList<>();
        titles.add(new TitlesDTO("Software Engineer", false, departmentId));
        titles.add(new TitlesDTO("Project Manager", true, departmentId));

        when(titlesServices.getTitlesByDepartment(departmentId)).thenReturn(titles);

        mockMvc.perform(get("/api/titles/getByDepartment/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"title\":\"Software Engineer\",\"isManager\":false,\"departmentId\":\"" + departmentId + "\"}," +
                        "{\"title\":\"Project Manager\",\"isManager\":true,\"departmentId\":\"" + departmentId + "\"}]"));

        verify(titlesServices, times(1)).getTitlesByDepartment(departmentId);
    }

    @Test
    public void getTitlesByDepartment_departmentNotFound_ReturnsNotFound() throws Exception {

        UUID departmentId = UUID.randomUUID();

        when(titlesServices.getTitlesByDepartment(departmentId))
                .thenThrow(new EntityNotFoundException("Department with id " + departmentId + " not found"));

        mockMvc.perform(get("/api/titles/getByDepartment/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department with id " + departmentId + " not found"));

        verify(titlesServices, times(1)).getTitlesByDepartment(departmentId);
    }

    @Test
    public void getTitlesByDepartment_noTitlesFound_ReturnsEmptyList() throws Exception {
        UUID departmentId = UUID.randomUUID();
        List<TitlesDTO> emptyList = new ArrayList<>();

        when(titlesServices.getTitlesByDepartment(departmentId)).thenReturn(emptyList);

        mockMvc.perform(get("/api/titles/getByDepartment/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(titlesServices, times(1)).getTitlesByDepartment(departmentId);
    }
}
