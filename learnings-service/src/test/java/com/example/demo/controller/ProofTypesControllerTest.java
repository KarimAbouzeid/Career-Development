package com.example.demo.controller;

import com.example.demo.controllers.ProofTypesController;
import com.example.demo.dtos.ProofTypesDTO;
import com.example.demo.services.ProofTypesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProofTypesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProofTypesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProofTypesService proofTypesService;

    private List<ProofTypesDTO> proofTypesList;

    @BeforeEach
    public void setUp() {

        proofTypesList = new ArrayList<>();
        proofTypesList.add(new ProofTypesDTO(UUID.randomUUID(), "Certificate"));
        proofTypesList.add(new ProofTypesDTO(UUID.randomUUID(), "Progress"));

    }

    @Test
    public void getAllProofTypes_ReturnsProofTypesList_Success() throws Exception {
        when(proofTypesService.getAllProofTypes()).thenReturn(proofTypesList);

        mockMvc.perform(get("/api/proofTypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Certificate"))
                .andExpect(jsonPath("$[1].name").value("Progress"));

        verify(proofTypesService, times(1)).getAllProofTypes();
    }

    @Test
    public void getAllProofTypes_EmptyList_Success() throws Exception {
        when(proofTypesService.getAllProofTypes()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/proofTypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(proofTypesService, times(1)).getAllProofTypes();
    }
}
