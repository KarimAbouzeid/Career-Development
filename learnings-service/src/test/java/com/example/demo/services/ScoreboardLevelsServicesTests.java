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
    @BeforeEach
    public void setUp() {
        scoreboardLevelsDTO1 = new ScoreboardLevelsDTO("Expert", 100);
        scoreboardLevelsDTO1 = new ScoreboardLevelsDTO("Professional", 200);
        scoreboardLevels1 = new ScoreboardLevels("Expert", 100);
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


}
