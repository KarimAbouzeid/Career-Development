package com.example.demo.services;

import com.example.demo.dtos.BoostersDTO;
import com.example.demo.entities.Boosters;
import com.example.demo.mappers.BoostersMapper;
import com.example.demo.repositories.BoostersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoostersServiceTest {

    @Mock
    private BoostersRepository boostersRepository;

    @Mock
    private BoostersMapper boostersMapper;

    @InjectMocks
    private BoostersService boostersService;

    private BoostersDTO boostersDTO;
    private Boosters boosters;

    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        boostersDTO = new BoostersDTO();
        boostersDTO.setId(id);
        boosters = new Boosters();
        boosters.setId(id);
    }

    @Test
    void createBooster_shouldSaveBooster() {
        when(boostersMapper.toBoosters(boostersDTO)).thenReturn(boosters);
        when(boostersRepository.save(boosters)).thenReturn(boosters);

        boostersService.createBooster(boostersDTO);

        verify(boostersMapper).toBoosters(boostersDTO);
        verify(boostersRepository).save(boosters);
    }

    @Test
    void getBoosterById_shouldReturnBoosterDTO() {
        UUID id = boostersDTO.getId();
        when(boostersRepository.findById(id)).thenReturn(Optional.of(boosters));
        when(boostersMapper.toBoostersDTO(boosters)).thenReturn(boostersDTO);

        BoostersDTO result = boostersService.getBoosterById(id);

        assertEquals(boostersDTO, result);
        verify(boostersRepository).findById(id);
    }

    @Test
    void getBoosterById_shouldThrowEntityNotFoundException_whenNotFound() {
        UUID id = boostersDTO.getId();
        when(boostersRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> boostersService.getBoosterById(id));

        assertEquals("Booster not found with id " + id, exception.getMessage());
    }

    @Test
    void getAllBoosters_shouldReturnListOfBoostersDTOs() {
        when(boostersRepository.findAll()).thenReturn(Collections.singletonList(boosters));
        when(boostersMapper.toBoostersDTO(boosters)).thenReturn(boostersDTO);

        List<BoostersDTO> result = boostersService.getAllBoosters();

        assertEquals(1, result.size());
        assertEquals(boostersDTO, result.get(0));
        verify(boostersRepository).findAll();
    }

    @Test
    void activateBooster_shouldActivateBooster() {
        UUID id = boostersDTO.getId();

        when(boostersRepository.findById(id)).thenReturn(Optional.of(boosters));
        when(boostersMapper.toBoostersDTO(boosters)).thenReturn(boostersDTO);

        boostersDTO.setActive(false);

        String result = boostersService.activateBooster(id);

        assertTrue(boostersDTO.isActive());
        assertEquals("Booster activated successfully.", result);

    }


    @Test
    void deactivateBooster_shouldDeactivateBooster() {
        UUID id = boostersDTO.getId();

        when(boostersRepository.findById(id)).thenReturn(Optional.of(boosters));
        when(boostersMapper.toBoostersDTO(boosters)).thenReturn(boostersDTO);

        boostersDTO.setActive(true);

        String result = boostersService.deactivateBooster(id);

        assertFalse(boostersDTO.isActive());
        assertEquals("Booster deactivated successfully.", result);

    }
}
