package com.example.demo.services;

import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.entities.ScoreboardLevels;
import com.example.demo.mappers.ScoreboardLevelsMapper;
import com.example.demo.repositories.ScoreboardLevelsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScoreboardLevelsService {

    private final ScoreboardLevelsRepository scoreboardLevelsRepository;
    private final ScoreboardLevelsMapper scoreboardLevelsMapper;


    @Autowired
    public ScoreboardLevelsService(ScoreboardLevelsRepository scoreboardLevelsRepository, ScoreboardLevelsMapper scoreboardLevelsMapper) {
        this.scoreboardLevelsMapper = scoreboardLevelsMapper;
        this.scoreboardLevelsRepository = scoreboardLevelsRepository;
    }

    public ScoreboardLevelsDTO getScoreboardLevel(UUID id) {

        ScoreboardLevels scoreboardLevels = scoreboardLevelsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scoreboard Level with id " + id + " not found"));

        return scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevels);
    }


    public ScoreboardLevelsDTO addScoreboardLevel(ScoreboardLevelsDTO scoreboardLevelsDTO) {
        ScoreboardLevels scoreboardLevel = scoreboardLevelsMapper.toScoreboardLevels(scoreboardLevelsDTO);
        scoreboardLevelsRepository.save(scoreboardLevel);
        return scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevel);
    }

    public ScoreboardLevelsDTO updateScoreboardLevel(UUID id, ScoreboardLevelsDTO scoreboardLevelsUpdateDTO) {

        ScoreboardLevels scoreboardLevel = scoreboardLevelsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ScoreboardLevel not found with ID: " + id));

        scoreboardLevelsMapper.updateScoreboardLevelsFromDTO(scoreboardLevelsUpdateDTO,scoreboardLevel);

        scoreboardLevelsRepository.save(scoreboardLevel);
        return scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevel);
    }

    public void deletescoreboardLevel(UUID id) {
        if (scoreboardLevelsRepository.existsById(id)) {
            scoreboardLevelsRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("ScoreboardLevel with id " + id + " does not exist.");

        }
    }


    public Page<ScoreboardLevelsDTO> getAllScoreboardLevels(Pageable pageable) {

        Page<ScoreboardLevels> scoreboardLevelsPage = scoreboardLevelsRepository.findAll(pageable);

        if (scoreboardLevelsPage.isEmpty()) {
            throw new EntityNotFoundException("No ScoreboardLevel found");
        }

        return scoreboardLevelsPage.map(scoreboardLevelsMapper::toScoreboardLevelsDTO);

    }


    public String getLevelByScore(int score) {
        List<ScoreboardLevels> levels = scoreboardLevelsRepository.findAll();

        for (ScoreboardLevels level : levels) {
            if (score <= level.getMinScore()) {
                return level.getLevelName();
            }
        }
        return "Guru";
    }

}
