package com.example.demo.services;

import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.entities.LearningTypes;
import com.example.demo.entities.Learnings;
import com.example.demo.entities.UserLearnings;
import com.example.demo.entities.UserScores;
import com.example.demo.mappers.UserScoresMapper;
import com.example.demo.repositories.UserLearningsRepository;
import com.example.demo.repositories.UserScoresRepository;
import com.example.demo.services.UserScoresService;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserScoresServiceTests {

    @Mock
    private UserScoresRepository userScoresRepository;

    @Mock
    private UserLearningsRepository userLearningsRepository;

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
        userScoresDTO.setUserId(userScoreId);
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

        UserScoresDTO result = userScoresService.updateUserScore(userScoresDTO);

        assertNotNull(result);
        assertEquals(100, result.getScore());
        verify(userScoresRepository, times(1)).findById(userScoreId);
        verify(userScoresRepository, times(1)).save(userScores);
    }

    @Test
    public void updateUserScore_userNotExists_ThrowsEntityNotFoundException() {
        when(userScoresRepository.findById(userScoreId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userScoresService.updateUserScore( userScoresDTO));

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


    @Test
    public void calculateUserScore_userLearningsExist_UpdatesUserScore() {
        UUID userId = UUID.randomUUID();
        UserScores userScores = new UserScores(userId, 0);
        UserLearnings userLearning1 = new UserLearnings();
        userLearning1.setLearning(new Learnings());
        userLearning1.getLearning().setLearningType(new LearningTypes());
        userLearning1.getLearning().getLearningType().setBaseScore(50);

        UserLearnings userLearning2 = new UserLearnings();
        userLearning2.setLearning(new Learnings());
        userLearning2.getLearning().setLearningType(new LearningTypes());
        userLearning2.getLearning().getLearningType().setBaseScore(100);

        List<UserLearnings> userLearnings = Arrays.asList(userLearning1, userLearning2);

        when(userLearningsRepository.existsByUserId(userId)).thenReturn(true);
        when(userLearningsRepository.findByUserId(userId)).thenReturn(userLearnings);
        when(userScoresRepository.findById(userId)).thenReturn(Optional.of(userScores));
        when(userScoresRepository.save(any(UserScores.class))).thenReturn(userScores);

        userScoresService.calculateUserScore(userId);

        assertEquals(150, userScores.getScore());
        verify(userScoresRepository, times(1)).save(userScores);
    }

    @Test
    public void calculateUserScore_userLearningsDoNotExist_CreatesUserScore() {
        UUID userId = UUID.randomUUID();
        UserScores userScores = new UserScores(userId, 0);

        when(userLearningsRepository.existsByUserId(userId)).thenReturn(false);
        when(userScoresRepository.findById(userId)).thenReturn(Optional.empty());
        when(userScoresRepository.save(any(UserScores.class))).thenReturn(userScores);

        userScoresService.calculateUserScore(userId);

        assertEquals(0, userScores.getScore());
        verify(userScoresRepository, times(1)).save(userScores);
    }

}