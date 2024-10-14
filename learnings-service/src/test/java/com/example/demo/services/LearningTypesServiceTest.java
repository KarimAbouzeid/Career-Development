package com.example.demo.services;

import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.entities.LearningTypes;
import com.example.demo.mappers.LearningTypesMapper;
import com.example.demo.repositories.LearningTypesRepository;
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
public class LearningTypesServiceTest {

    @Mock
    private LearningTypesRepository learningTypesRepository;

    @Mock
    private LearningsRepository learningsRepository;

    @Mock
    private LearningTypesMapper learningTypesMapper;

    @InjectMocks
    private LearningTypesService learningTypesService;

    private LearningTypes learningType;
    private LearningTypesDTO learningTypesDTO;

    @BeforeEach
    public void setup() {
        learningType = new LearningTypes();
        learningType.setId(UUID.randomUUID());
        learningType.setTypeName("Technical");

        learningTypesDTO = new LearningTypesDTO();
        learningTypesDTO.setId(learningType.getId());
        learningTypesDTO.setTypeName("Technical");
    }

    @Test
    public void getLearningTypeById_existingId_returnsLearningTypeDTO() {
        // Mock the repository and mapper behavior
        UUID id = learningType.getId();
        when(learningTypesRepository.findById(id)).thenReturn(Optional.of(learningType));
        when(learningTypesMapper.toLearningTypesDTO(any(LearningTypes.class))).thenReturn(learningTypesDTO);

        // Act
        LearningTypesDTO result = learningTypesService.getLearningTypeById(id);

        // Assert
        assertNotNull(result);
        assertEquals(learningTypesDTO.getId(), result.getId());
        assertEquals(learningTypesDTO.getTypeName(), result.getTypeName());
        verify(learningTypesRepository, times(1)).findById(id);
        verify(learningTypesMapper, times(1)).toLearningTypesDTO(learningType);
    }

    @Test
    public void getLearningTypeById_nonExistingId_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(learningTypesRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> learningTypesService.getLearningTypeById(id));
        verify(learningTypesRepository, times(1)).findById(id);
    }

    @Test
    public void getLearningTypes_returnsListOfLearningTypesDTO() {
        when(learningTypesRepository.findAll()).thenReturn(Collections.singletonList(learningType));
        when(learningTypesMapper.toLearningTypesDTO(any(LearningTypes.class))).thenReturn(learningTypesDTO);

        // Act
        List<LearningTypesDTO> result = learningTypesService.getLearningTypes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(learningTypesDTO.getTypeName(), result.get(0).getTypeName());
        verify(learningTypesRepository, times(1)).findAll();
        verify(learningTypesMapper, times(1)).toLearningTypesDTO(any(LearningTypes.class));
    }

    @Test
    public void addLearningType_validDTO_savesLearningType() {
        when(learningTypesMapper.toLearningTypes(any(LearningTypesDTO.class))).thenReturn(learningType);
        when(learningTypesRepository.save(any(LearningTypes.class))).thenReturn(learningType);
        when(learningTypesMapper.toLearningTypesDTO(any(LearningTypes.class))).thenReturn(learningTypesDTO);

        // Act
        LearningTypesDTO result = learningTypesService.addLearningType(learningTypesDTO);

        // Assert
        assertNotNull(result);
        assertEquals(learningTypesDTO.getTypeName(), result.getTypeName());
        verify(learningTypesRepository, times(1)).save(any(LearningTypes.class));
        verify(learningTypesMapper, times(1)).toLearningTypesDTO(any(LearningTypes.class));
    }

    @Test
    public void updateLearningType_existingId_updatesLearningType() {
        UUID id = learningType.getId();
        when(learningTypesRepository.findById(id)).thenReturn(Optional.of(learningType));
        when(learningTypesMapper.toLearningTypesDTO(any(LearningTypes.class))).thenReturn(learningTypesDTO);

        // Act
        LearningTypesDTO result = learningTypesService.updateLearningType(id, learningTypesDTO);

        // Assert
        assertNotNull(result);
        verify(learningTypesRepository, times(1)).findById(id);
        verify(learningTypesRepository, times(1)).save(learningType);
        verify(learningTypesMapper, times(1)).updateLearningTypesFromDTO(any(LearningTypesDTO.class), eq(learningType));
    }

    @Test
    public void updateLearningType_nonExistingId_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(learningTypesRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> learningTypesService.updateLearningType(id, learningTypesDTO));
        verify(learningTypesRepository, times(1)).findById(id);
    }

    @Test
    public void deleteLearningType_existingId_deletesLearningType() {
        UUID id = learningType.getId();
        when(learningTypesRepository.existsById(id)).thenReturn(true);

        // Act
        learningTypesService.deleteLearningType(id);

        // Assert
        verify(learningsRepository, times(1)).deleteBylearningTypeId(id);
        verify(learningTypesRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteLearningType_nonExistingId_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        when(learningTypesRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> learningTypesService.deleteLearningType(id));
        verify(learningTypesRepository, times(1)).existsById(id);
        verify(learningTypesRepository, never()).deleteById(id);
    }
}
