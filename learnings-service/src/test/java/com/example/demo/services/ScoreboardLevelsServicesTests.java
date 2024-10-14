package com.example.demo.services;



import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.entities.ScoreboardLevels;
import com.example.demo.mappers.ScoreboardLevelsMapper;
import com.example.demo.repositories.ScoreboardLevelsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScoreboardLevelsServicesTests {

    @Mock
    private ScoreboardLevelsRepository scoreboardLevelsRepository;

    @Mock
    private ScoreboardLevelsMapper scoreboardLevelsMapper;

    @InjectMocks
    private ScoreboardLevelsService scoreboardLevelsService;


    // Variables
    private ScoreboardLevelsDTO scoreboardLevelsDTO1;
    private ScoreboardLevelsDTO scoreboardLevelsDTO2;
    private ScoreboardLevels scoreboardLevels1;
    private ScoreboardLevels scoreboardLevels2;

    @BeforeEach
    public void setUp() {
        UUID scoreboardLevelId = UUID.randomUUID();
        UUID scoreboardLevelId2 = UUID.randomUUID();
        scoreboardLevelsDTO1 = new ScoreboardLevelsDTO(scoreboardLevelId,"Professional", 200);
        scoreboardLevels1 = new ScoreboardLevels("Expert", 100);

        scoreboardLevelsDTO2 = new ScoreboardLevelsDTO(scoreboardLevelId2, "Professional", 200);
        scoreboardLevels2 = new ScoreboardLevels("Professional", 200);

    }


    @Test
    public void ScoreboardLevelsService_GetScoreboardLevel_ReturnsScoreboardLevelsDTO() {
        UUID uuid = UUID.randomUUID();

        when(scoreboardLevelsRepository.findById(uuid)).thenReturn(Optional.of(scoreboardLevels1));
        when(scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevels1)).thenReturn(scoreboardLevelsDTO1);

        ScoreboardLevelsDTO returnedScoreboardLevelDto = scoreboardLevelsService.getScoreboardLevel(uuid);

        verify(scoreboardLevelsRepository, times(1)).findById(uuid);
        Assertions.assertEquals(returnedScoreboardLevelDto, scoreboardLevelsDTO1);
    }

    @Test
    public void ScoreboardLevelsService_GetDepartment_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(scoreboardLevelsRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> scoreboardLevelsService.getScoreboardLevel(uuid));

        verify(scoreboardLevelsRepository, times(1)).findById(uuid);
    }

    @Test
    public void ScoreboardLevelsService_AddScoreboardLevels_ReturnsScoreboardLevelsDTO() {

        when(scoreboardLevelsMapper.toScoreboardLevels(any(ScoreboardLevelsDTO.class))).thenReturn(scoreboardLevels1);
        when(scoreboardLevelsMapper.toScoreboardLevelsDTO(any(ScoreboardLevels.class))).thenReturn(scoreboardLevelsDTO1);

        ScoreboardLevelsDTO returnedScoreboardLevelsDTO = scoreboardLevelsService.addScoreboardLevel(scoreboardLevelsDTO1);

        verify(scoreboardLevelsRepository, times(1)).save(scoreboardLevels1);
        Assertions.assertEquals(returnedScoreboardLevelsDTO, scoreboardLevelsDTO1);
    }

    @Test
    public void ScoreboardLevelsService_UpdateScoreboardLevels_ReturnsScoreboardLevelsDTO() {

        UUID uuid = UUID.randomUUID();

        when(scoreboardLevelsRepository.findById(any(UUID.class))).thenReturn(Optional.of(scoreboardLevels1));

        when(scoreboardLevelsMapper.toScoreboardLevelsDTO(any(ScoreboardLevels.class))).thenReturn(scoreboardLevelsDTO1);
        ScoreboardLevelsDTO returnedScoreboardLevelsDTO = scoreboardLevelsService.updateScoreboardLevel(uuid, scoreboardLevelsDTO1);



        verify(scoreboardLevelsMapper).updateScoreboardLevelsFromDTO(any(ScoreboardLevelsDTO.class), eq(scoreboardLevels1));
        verify(scoreboardLevelsRepository).save(scoreboardLevels1);
        Assertions.assertEquals(returnedScoreboardLevelsDTO, scoreboardLevelsDTO1);


    }

    @Test
    public void ScoreboardLevelsService_UpdateScoreboardLevels_ReturnsEntityNotFoundException() {

        UUID uuid = UUID.randomUUID();

        when(scoreboardLevelsRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()-> scoreboardLevelsService.updateScoreboardLevel(uuid, scoreboardLevelsDTO1));

    }


    @Test
    public void ScoreboardLevelsService_DeleteDepartment_Success() {
        UUID uuid = UUID.randomUUID();

        when(scoreboardLevelsRepository.existsById(uuid)).thenReturn(true);

        scoreboardLevelsService.deletescoreboardLevel(uuid);

        verify(scoreboardLevelsRepository, times(1)).deleteById(uuid);
    }

    @Test
    public void ScoreboardLevelsService_DeleteDepartment_ThrowsEntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(scoreboardLevelsRepository.existsById(uuid)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> scoreboardLevelsService.deletescoreboardLevel(uuid));

        verify(scoreboardLevelsRepository, times(1)).existsById(uuid);
        verify(scoreboardLevelsRepository, never()).deleteById(uuid);
    }


    @Test
    public void ScoreboardLevelsService_GetAllScoreboardLevels_ReturnsListOfScoreboardLevelsDTO() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        List<ScoreboardLevels> levels = List.of(scoreboardLevels1, scoreboardLevels2);
        Page<ScoreboardLevels> scoreboardLevelsPage = new PageImpl<>(levels);

        when(scoreboardLevelsRepository.findAll(pageable)).thenReturn(scoreboardLevelsPage);
        when(scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevels1)).thenReturn(scoreboardLevelsDTO1);
        when(scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevels2)).thenReturn(scoreboardLevelsDTO2);

        // Act
        Page<ScoreboardLevelsDTO> result = scoreboardLevelsService.getAllScoreboardLevels(pageable);

        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals(scoreboardLevelsDTO1, result.getContent().get(0));
        Assertions.assertEquals(scoreboardLevelsDTO2, result.getContent().get(1));
    }

    @Test
    public void ScoreboardLevelsService_GetAllScoreboardLevels_ThrowsEntityNotFoundException() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        Page<ScoreboardLevels> emptyPage = new PageImpl<>(Collections.emptyList());

        when(scoreboardLevelsRepository.findAll(pageable)).thenReturn(emptyPage);

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> scoreboardLevelsService.getAllScoreboardLevels(pageable));
    }

    @Test
    public void ScoreboardLevelsService_GetLevelByScore_ReturnsCorrectLevel() {
        // Arrange
        when(scoreboardLevelsRepository.findAll()).thenReturn(List.of(scoreboardLevels1, scoreboardLevels2));

        // Act
        String level = scoreboardLevelsService.getLevelByScore(150);

        // Assert
        Assertions.assertEquals("Professional", level);
    }

    @Test
    public void ScoreboardLevelsService_GetLevelByScore_ReturnsGuruForHighScore() {
        // Arrange
        when(scoreboardLevelsRepository.findAll()).thenReturn(List.of(scoreboardLevels1, scoreboardLevels2));

        // Act
        String level = scoreboardLevelsService.getLevelByScore(250);

        // Assert
        Assertions.assertEquals("Guru", level);
    }



}
