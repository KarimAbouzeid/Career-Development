package com.example.demo.services;

import com.example.demo.dtos.ProofTypesDTO;
import com.example.demo.entities.ProofTypes;
import com.example.demo.mappers.ProofTypesMapper;
import com.example.demo.repositories.ProofTypesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProofTypesServiceTest {

    @Mock
    private ProofTypesRepository proofTypesRepository;

    @Mock
    private ProofTypesMapper proofTypesMapper;

    @InjectMocks
    private ProofTypesService proofTypesService;

    private ProofTypes proofType;
    private ProofTypesDTO proofTypesDTO;

    @BeforeEach
    public void setup() {
        proofType = new ProofTypes();
        proofType.setId(UUID.randomUUID());
        proofType.setName("Document");

        proofTypesDTO = new ProofTypesDTO();
        proofTypesDTO.setId(proofType.getId());
        proofTypesDTO.setName(proofType.getName());
    }

    @Test
    public void getAllProofTypes_returnsListOfProofTypesDTO() {
        when(proofTypesRepository.findAll()).thenReturn(Collections.singletonList(proofType));
        when(proofTypesMapper.toProofTypesDTO(any(ProofTypes.class))).thenReturn(proofTypesDTO);

        List<ProofTypesDTO> result = proofTypesService.getAllProofTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(proofTypesDTO.getName(), result.get(0).getName());
        verify(proofTypesRepository, times(1)).findAll();
        verify(proofTypesMapper, times(1)).toProofTypesDTO(any(ProofTypes.class));
    }

    @Test
    public void getAllProofTypes_returnsEmptyList_whenNoProofTypesExist() {
        when(proofTypesRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProofTypesDTO> result = proofTypesService.getAllProofTypes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(proofTypesRepository, times(1)).findAll();
        verify(proofTypesMapper, never()).toProofTypesDTO(any(ProofTypes.class));
    }
}
