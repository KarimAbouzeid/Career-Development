package com.example.demo.services;

import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.entities.UserScores;
import com.example.demo.mappers.UserScoresMapper;
import com.example.demo.repositories.UserLearningsRepository;
import com.example.demo.repositories.UserScoresRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserScoresService {

    private final UserScoresRepository usersScoresRepository;
    private final UserScoresMapper userScoresMapper;
    private final UserLearningsRepository userLearningsRepository;

    @Autowired
    public UserScoresService(UserScoresRepository usersScoresRepository, UserScoresMapper userScoresMapper, UserLearningsRepository userLearningsRepository) {
        this.usersScoresRepository = usersScoresRepository;
        this.userScoresMapper = userScoresMapper;
        this.userLearningsRepository = userLearningsRepository;
    }

    public UserScoresDTO getUserScore(UUID id) {
        UserScores userScore = usersScoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return userScoresMapper.toUserScoresDTO(userScore);
    }


    public UserScoresDTO updateUserScore(UserScoresDTO userScoresDTO) {
        UserScores existingScore = usersScoresRepository.findById(userScoresDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userScoresDTO.getUserId() + " not found"));
        userScoresMapper.updateUserScoresFromDTO(userScoresDTO, existingScore);
        UserScores updatedScore = usersScoresRepository.save(existingScore);
        return userScoresMapper.toUserScoresDTO(updatedScore);
    }


    public String calculateUserScore(UUID userId) {
        // Calculate the total score based on UserLearnings
        int totalScore = userLearningsRepository.findByUserId(userId).stream()
                .mapToInt(userLearning -> userLearning.getLearning().getLearningType().getBaseScore())
                .sum();

        UserScores userScores = usersScoresRepository.findById(userId)
                .orElse(new UserScores(userId, 0));
        userScores.setScore(totalScore);
        usersScoresRepository.save(userScores);

        return "User score updated successfully to " + totalScore;
    }

    public UserScoresDTO addUserScore(UserScoresDTO userScoresDTO) {

        UserScores userScores = userScoresMapper.toUserScores(userScoresDTO);
        userScores.setUserId(userScoresDTO.getUserId());

        UserScores savedUserScore = usersScoresRepository.save(userScores);
        return userScoresMapper.toUserScoresDTO(savedUserScore);
    }

    public void deleteUserScore(UUID id) {
        UserScores userScore = usersScoresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        usersScoresRepository.delete(userScore);
    }

    public Page<UserScoresDTO> getAllUserScores(Pageable pageable) {
        Page<UserScores> usersPage = usersScoresRepository.findAll(pageable);

        if (usersPage.isEmpty()) {
            throw new EntityNotFoundException("No user scores found");
        }

        return usersPage.map(userScoresMapper::toUserScoresDTO);
    }


}