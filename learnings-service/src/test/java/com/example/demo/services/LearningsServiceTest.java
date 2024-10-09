package com.example.demo.services;

import com.example.demo.dtos.LearningsDTO;
import com.example.demo.entities.Learnings;
import com.example.demo.mappers.LearningsMapper;
import com.example.demo.repositories.LearningsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningsServiceTest {

    @Mock
    private LearningsRepository learningsRepository;

    @Mock
    private LearningsMapper learningsMapper;

    @InjectMocks
    private LearningsService learningsService;

    private LearningsDTO learningsDTO;
    private Learnings learnings;

    @BeforeEach
    public void setup() {
        learningsDTO = new LearningsDTO();
        learningsDTO.setId(UUID.randomUUID());

        learnings = new Learnings();
        learnings.setId(learningsDTO.getId());
    }

    @Test
    public void getAllLearnings_returnsListOfLearnings() {
        when(learningsRepository.findAll()).thenReturn(Collections.singletonList(learnings));
        when(learningsMapper.toLearningsDTO(any(Learnings.class))).thenReturn(learningsDTO);

        List<LearningsDTO> result = learningsService.getAllLearnings();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(learningsRepository, times(1)).findAll();
    }

    @Test
    public void getLearningById_learningExists_returnsLearning() {
        UUID id = UUID.randomUUID();
        when(learningsRepository.findById(id)).thenReturn(Optional.of(learnings));
        when(learningsMapper.toLearningsDTO(any(Learnings.class))).thenReturn(learningsDTO);

        LearningsDTO result = learningsService.getLearningById(id);

        assertNotNull(result);
        verify(learningsRepository, times(1)).findById(id);
    }

    @Test
    public void getLearningById_learningDoesNotExist_throwsException() {
        UUID id = UUID.randomUUID();
        when(learningsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> learningsService.getLearningById(id));
        verify(learningsRepository, times(1)).findById(id);
    }

    @Test
    public void addLearning_validLearning_savesSuccessfully() {
        when(learningsMapper.toLearnings(any(LearningsDTO.class))).thenReturn(learnings);
        when(learningsRepository.save(any(Learnings.class))).thenReturn(learnings);

        learningsService.addLearning(learningsDTO);

        verify(learningsRepository, times(1)).save(any(Learnings.class));
        verify(learningsMapper, times(1)).toLearningsDTO(any(Learnings.class));
    }

    @Test
    public void updateLearning_learningExists_updatesSuccessfully() {
        UUID id = UUID.randomUUID();
        when(learningsRepository.findById(id)).thenReturn(Optional.of(learnings));

        learningsService.updateLearning(id, learningsDTO);

        verify(learningsRepository, times(1)).findById(id);
        verify(learningsRepository, times(1)).save(any(Learnings.class));
    }

    @Test
    public void updateLearning_learningDoesNotExist_throwsException() {
        UUID id = UUID.randomUUID();
        when(learningsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> learningsService.updateLearning(id, learningsDTO));
        verify(learningsRepository, times(1)).findById(id);
    }
}
