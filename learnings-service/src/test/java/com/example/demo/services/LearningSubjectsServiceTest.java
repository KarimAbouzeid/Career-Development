package com.example.demo.services;

import com.example.demo.dtos.LearningSubjectsDTO;
import com.example.demo.entities.LearningSubjects;
import com.example.demo.mappers.LearningSubjectsMapper;
import com.example.demo.repositories.LearningSubjectsRepository;
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
public class LearningSubjectsServiceTest {

    @Mock
    private LearningSubjectsRepository learningSubjectsRepository;

    @Mock
    private LearningsRepository learningsRepository;

    @Mock
    private LearningSubjectsMapper learningSubjectsMapper;

    @InjectMocks
    private LearningSubjectsService learningSubjectsService;

    private LearningSubjects learningSubject;
    private LearningSubjectsDTO learningSubjectsDTO;

    @BeforeEach
    public void setup() {
        learningSubject = new LearningSubjects();
        learningSubject.setId(UUID.randomUUID());
        learningSubject.setSubject("Math");

        learningSubjectsDTO = new LearningSubjectsDTO();
        learningSubjectsDTO.setId(learningSubject.getId());
        learningSubjectsDTO.setSubject("Math");
    }

    @Test
    public void getLearningSubjectById_existingId_returnsLearningSubjectsDTO() {
        // Mock the repository and mapper behavior
        UUID id = learningSubject.getId();
        when(learningSubjectsRepository.findById(id)).thenReturn(Optional.of(learningSubject));
        when(learningSubjectsMapper.toLearningSubjectsDTO(any(LearningSubjects.class))).thenReturn(learningSubjectsDTO);

        // Act
        LearningSubjectsDTO result = learningSubjectsService.getLearningSubjectById(id);

        // Assert
        assertNotNull(result);
        assertEquals(learningSubjectsDTO.getId(), result.getId());
        assertEquals(learningSubjectsDTO.getSubject(), result.getSubject());
        verify(learningSubjectsRepository, times(1)).findById(id);
        verify(learningSubjectsMapper, times(1)).toLearningSubjectsDTO(learningSubject);
    }

    @Test
    public void getLearningSubjectById_nonExistingId_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(learningSubjectsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> learningSubjectsService.getLearningSubjectById(id));
        verify(learningSubjectsRepository, times(1)).findById(id);
    }

    @Test
    public void getLearningSubjects_returnsListOfLearningSubjectsDTO() {
        when(learningSubjectsRepository.findAll()).thenReturn(Collections.singletonList(learningSubject));
        when(learningSubjectsMapper.toLearningSubjectsDTO(any(LearningSubjects.class))).thenReturn(learningSubjectsDTO);

        // Act
        List<LearningSubjectsDTO> result = learningSubjectsService.getLearningSubjects();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(learningSubjectsDTO.getSubject(), result.get(0).getSubject());
        verify(learningSubjectsRepository, times(1)).findAll();
        verify(learningSubjectsMapper, times(1)).toLearningSubjectsDTO(any(LearningSubjects.class));
    }

    @Test
    public void addLearningSubject_validDTO_savesLearningSubject() {
        when(learningSubjectsMapper.toLearningSubjects(any(LearningSubjectsDTO.class))).thenReturn(learningSubject);
        when(learningSubjectsRepository.save(any(LearningSubjects.class))).thenReturn(learningSubject);
        when(learningSubjectsMapper.toLearningSubjectsDTO(any(LearningSubjects.class))).thenReturn(learningSubjectsDTO);

        // Act
        LearningSubjectsDTO result = learningSubjectsService.addLearningSubject(learningSubjectsDTO);

        // Assert
        assertNotNull(result);
        assertEquals(learningSubjectsDTO.getSubject(), result.getSubject());
        verify(learningSubjectsRepository, times(1)).save(any(LearningSubjects.class));
        verify(learningSubjectsMapper, times(1)).toLearningSubjectsDTO(any(LearningSubjects.class));
    }

    @Test
    public void updateLearningSubject_existingId_updatesLearningSubject() {
        UUID id = learningSubject.getId();
        when(learningSubjectsRepository.findById(id)).thenReturn(Optional.of(learningSubject));
        when(learningSubjectsMapper.toLearningSubjectsDTO(any(LearningSubjects.class))).thenReturn(learningSubjectsDTO);

        // Act
        LearningSubjectsDTO result = learningSubjectsService.updateLearningSubject(id, learningSubjectsDTO);

        // Assert
        assertNotNull(result);
        verify(learningSubjectsRepository, times(1)).findById(id);
        verify(learningSubjectsRepository, times(1)).save(learningSubject);
        verify(learningSubjectsMapper, times(1)).updateLearningSubjectsFromDTO(any(LearningSubjectsDTO.class), eq(learningSubject));
    }

    @Test
    public void updateLearningSubject_nonExistingId_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(learningSubjectsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> learningSubjectsService.updateLearningSubject(id, learningSubjectsDTO));
        verify(learningSubjectsRepository, times(1)).findById(id);
    }

    @Test
    public void deleteLearningSubject_existingId_deletesLearningSubject() {
        UUID id = learningSubject.getId();
        when(learningSubjectsRepository.existsById(id)).thenReturn(true);

        // Act
        learningSubjectsService.deleteLearningSubject(id);

        // Assert
        verify(learningsRepository, times(1)).deleteByLearningSubjectId(id);
        verify(learningSubjectsRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteLearningSubject_nonExistingId_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(learningSubjectsRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> learningSubjectsService.deleteLearningSubject(id));
        verify(learningSubjectsRepository, times(1)).existsById(id);
        verify(learningSubjectsRepository, never()).deleteById(id);
    }
}
