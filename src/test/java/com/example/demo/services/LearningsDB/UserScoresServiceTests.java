package com.example.demo.services.LearningsDB;

import com.example.demo.dtos.learningsDB.UserScoresDTO;
import com.example.demo.entities.LearningsDB.UserScores;
import com.example.demo.mappers.LearningsDB.UserScoresMapper;
import com.example.demo.repositories.LearningsDB.UserScoresRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserScoresServiceTests {

    @Mock
    private UserScoresRepository userScoresRepository;

    @Mock
    private UserScoresMapper userScoresMapper;

    @InjectMocks
    private UserScoresService userScoresService;

    private UserScores userScores;
    private UserScoresDTO userScoresDTO;
    private UUID userScoreId;

    @BeforeEach
    public void setUp() {
        userScoreId = UUID.randomUUID();
        userScores = new UserScores();
        userScores.setUserId(userScoreId);
        userScores.setScore(100);

        userScoresDTO = new UserScoresDTO();
        userScoresDTO.setScore(100);
    }

    @Test
    public void getUserScore_userExists_ReturnsUserScoresDTO() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.of(userScores));
        when(userScoresMapper.toUserScoresDTO(userScores)).thenReturn(userScoresDTO);

        UserScoresDTO result = userScoresService.getUserScore(userScoreId);

        assertNotNull(result);
        assertEquals(100, result.getScore());
        verify(userScoresRepository, times(1)).findById(userScoreId);
    }

    @Test
    public void getUserScore_userNotExists_ThrowsEntityNotFoundException() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userScoresService.getUserScore(userScoreId));

        verify(userScoresRepository, times(1)).findById(userScoreId);
    }

    @Test
    public void addUserScore_validInput_ReturnsUserScoresDTO() {
        when(userScoresMapper.toUserScores(userScoresDTO)).thenReturn(userScores);
        when(userScoresRepository.save(userScores)).thenReturn(userScores);
        when(userScoresMapper.toUserScoresDTO(userScores)).thenReturn(userScoresDTO);

        UserScoresDTO result = userScoresService.addUserScore(userScoresDTO);

        assertNotNull(result);
        assertEquals(100, result.getScore());

        verify(userScoresRepository, times(1)).save(userScores);
    }

    @Test
    public void updateUserScore_userExists_ReturnsUpdatedUserScoresDTO() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.of(userScores));
        doNothing().when(userScoresMapper).updateUserScoresFromDTO(userScoresDTO, userScores);
        when(userScoresRepository.save(userScores)).thenReturn(userScores);
        when(userScoresMapper.toUserScoresDTO(userScores)).thenReturn(userScoresDTO);

        UserScoresDTO result = userScoresService.updateUserScore(userScoreId, userScoresDTO);

        assertNotNull(result);
        assertEquals(100, result.getScore());
        verify(userScoresRepository, times(1)).findById(userScoreId);
        verify(userScoresRepository, times(1)).save(userScores);
    }

    @Test
    public void updateUserScore_userNotExists_ThrowsEntityNotFoundException() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userScoresService.updateUserScore(userScoreId, userScoresDTO));

        verify(userScoresRepository, times(1)).findById(userScoreId);
        verify(userScoresRepository, never()).save(any());
    }

    @Test
    public void deleteUserScore_userExists_DeletesUserScore() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.of(userScores));

        userScoresService.deleteUserScore(userScoreId);

        verify(userScoresRepository, times(1)).findById(userScoreId);
        verify(userScoresRepository, times(1)).delete(userScores);
    }

    @Test
    public void deleteUserScore_userNotExists_ThrowsEntityNotFoundException() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userScoresService.deleteUserScore(userScoreId));

        verify(userScoresRepository, times(1)).findById(userScoreId);
        verify(userScoresRepository, never()).delete(any());
    }

    @Test
    public void getAllUserScores_validRequest_ReturnsUserScoresPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserScores> userScoresPage = new PageImpl<>(Collections.singletonList(userScores));
        when(userScoresRepository.findAll(pageable)).thenReturn(userScoresPage);
        when(userScoresMapper.toUserScoresDTO(userScores)).thenReturn(userScoresDTO);

        Page<UserScoresDTO> result = userScoresService.getAllUserScores(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userScoresRepository, times(1)).findAll(pageable);
    }

    @Test
    public void getAllUserScores_noScoresFound_ThrowsEntityNotFoundException() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserScores> emptyPage = Page.empty();
        when(userScoresRepository.findAll(pageable)).thenReturn(emptyPage);

        assertThrows(EntityNotFoundException.class, () -> userScoresService.getAllUserScores(pageable));

        verify(userScoresRepository, times(1)).findAll(pageable);
    }
}
