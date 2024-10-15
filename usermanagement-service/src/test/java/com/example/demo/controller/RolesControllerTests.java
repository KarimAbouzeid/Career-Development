package com.example.demo.controller;


import com.example.demo.controllers.RolesController;
import com.example.demo.dtos.RoleDto;
import com.example.demo.services.RolesServices;
import exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolesController.class)
@ContextConfiguration(classes = {RolesController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
public class RolesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolesServices rolesServices;


    @Test
    void testAddRole() throws Exception {
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ROLE_USER");

        when(rolesServices.addRole(any(RoleDto.class))).thenReturn(roleDto);

        mockMvc.perform(post("/api/roles/addRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ROLE_USER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_USER"));
    }

}