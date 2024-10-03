package com.example.demo.controller;


import com.example.demo.controllers.DepartmentsController;
import com.example.demo.dtos.DepartmentsDTO;
import com.example.demo.services.DepartmentsServices;
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

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentsController.class)
@ContextConfiguration(classes = {DepartmentsController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
public class DepartmentsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentsServices departmentsServices;

    private DepartmentsDTO departmentsDTO;

    @BeforeEach
    public void setUp() {
        departmentsDTO = new DepartmentsDTO("Software Engineering");
    }

    @Test
    public void getDepartment_departmentExists_ReturnsDepartmentDTO() throws Exception {
        UUID departmentId = UUID.randomUUID();

        when(departmentsServices.getDepartment(departmentId)).thenReturn(departmentsDTO);

        mockMvc.perform(get("/api/departments/{id}", departmentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Software Engineering"));

        verify(departmentsServices, times(1)).getDepartment(departmentId);
    }

    @Test
    void getDepartment_departmentNotExists_returnsNotFound() throws Exception {
        UUID departmentId = UUID.randomUUID();
        when(departmentsServices.getDepartment(departmentId)).thenThrow(new EntityNotFoundException("Department with id " + departmentId + " not found"));

        mockMvc.perform(get("/api/departments/{id}", departmentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department with id " + departmentId + " not found"));
    }

    @Test
    void getDepartment_unexpectedError_returnsInternalServerError() throws Exception {
        UUID departmentId = UUID.randomUUID();
        when(departmentsServices.getDepartment(departmentId)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/departments/{id}", departmentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void addDepartment_departmentDetailsEntered_ReturnsDepartmentDTO() throws Exception {
        when(departmentsServices.addDepartment(any(DepartmentsDTO.class))).thenReturn(departmentsDTO);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Software Engineering\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Software Engineering"));

        verify(departmentsServices, times(1)).addDepartment(any(DepartmentsDTO.class));
    }

    @Test
    void addDepartment_unexpectedError_returnsInternalServerError() throws Exception {
        when(departmentsServices.addDepartment(any(DepartmentsDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Software Engineering\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void updateDepartment_departmentExists_ReturnsDepartmentDTO() throws Exception {
        UUID departmentId = UUID.randomUUID();

        when(departmentsServices.updateDepartment(eq(departmentId), any(DepartmentsDTO.class))).thenReturn(departmentsDTO);

        mockMvc.perform(put("/api/departments/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Department Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Software Engineering"));

        verify(departmentsServices, times(1)).updateDepartment(eq(departmentId), any(DepartmentsDTO.class));
    }

    @Test
    public void updateDepartment_departmentNotExists_ReturnsDepartmentDTO() throws Exception {
        UUID departmentId = UUID.randomUUID();

        when(departmentsServices.updateDepartment(eq(departmentId), any(DepartmentsDTO.class))).thenThrow(new EntityNotFoundException("Department with id " + departmentId + " not found"));

        mockMvc.perform(put("/api/departments/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Department Name\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department with id " + departmentId + " not found"));
    }

    @Test
    void updateDepartment_unexpectedError_returnsInternalServerError() throws Exception {
        UUID departmentId = UUID.randomUUID();

        when(departmentsServices.updateDepartment(eq(departmentId), any(DepartmentsDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/departments/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Department Name\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void deleteDepartment_departmentExists_ReturnsSuccessMessage() throws Exception {
        UUID departmentId = UUID.randomUUID();

        doNothing().when(departmentsServices).deleteDepartment(departmentId);

        mockMvc.perform(delete("/api/departments/{id}", departmentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Department deleted successfully"));

        verify(departmentsServices, times(1)).deleteDepartment(departmentId);
    }

    @Test
    public void deleteDepartment_departmentNotExists_ReturnsDepartmentDTO() throws Exception {
        UUID departmentId = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Department with id " + departmentId + " not found"))
                .when(departmentsServices).deleteDepartment(departmentId);


        mockMvc.perform(delete("/api/departments/{id}", departmentId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Department with id " + departmentId + " not found"));
    }

    @Test
    void deleteDepartment_unexpectedError_returnsInternalServerError() throws Exception {
        UUID departmentId = UUID.randomUUID();

        doThrow(new RuntimeException())
                .when(departmentsServices).deleteDepartment(departmentId);


        mockMvc.perform(delete("/api/departments/{id}", departmentId))
                .andExpect(status().isInternalServerError())
               .andExpect(content().string("An error occurred. Please try again later."));
    }

}
