package com.example.demo.mappers;


import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.entities.ScoreboardLevels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ScoreboardLevelsMapperTests {

    @Autowired
    private ScoreboardLevelsMapper scoreboardLevelsMapper;

    private ScoreboardLevels scoreboardLevels;
    private ScoreboardLevelsDTO scoreboardLevelsDTO;

    @BeforeEach
    public void setUp() {
        UUID scoreboardLevelsId = UUID.randomUUID();

        scoreboardLevels = new ScoreboardLevels();
        scoreboardLevels.setId(scoreboardLevelsId);
        scoreboardLevels.setLevelName("Expert");
        scoreboardLevels.setMinScore(85);

        scoreboardLevelsDTO = new ScoreboardLevelsDTO();
        scoreboardLevelsDTO.setLevelName("Expert");
        scoreboardLevelsDTO.setMinScore(85);

    }

    @Test
    public void toScoreboardLevelsDTO_validScoreboardLevels_returnsScoreboardLevelsDTO() {
        ScoreboardLevelsDTO dto = scoreboardLevelsMapper.toScoreboardLevelsDTO(scoreboardLevels);

        assertThat(dto).isNotNull();
        assertThat(dto.getMinScore()).isEqualTo(scoreboardLevels.getMinScore());
        assertThat(dto.getLevelName()).isEqualTo(scoreboardLevels.getLevelName());

    }

    @Test
    public void toScoreboardLevels_validScoreboardLevelsDTO_returnsScoreboardLevels() {
        ScoreboardLevels entity = scoreboardLevelsMapper.toScoreboardLevels(scoreboardLevelsDTO);

        assertThat(entity).isNotNull();
        assertThat(entity.getMinScore()).isEqualTo(scoreboardLevels.getMinScore());
        assertThat(entity.getLevelName()).isEqualTo(scoreboardLevels.getLevelName());
    }

    @Test
    public void updateScoreboardLevelsFromDTO_validScoreboardLevelsDTO_updatesScoreboardLevels() {
        ScoreboardLevels updatedScoreboardLevel = new ScoreboardLevels();
        updatedScoreboardLevel.setId(scoreboardLevels.getId());
        updatedScoreboardLevel.setMinScore(90);

        scoreboardLevelsMapper.updateScoreboardLevelsFromDTO(scoreboardLevelsDTO, updatedScoreboardLevel);

        assertThat(updatedScoreboardLevel.getMinScore()).isEqualTo(scoreboardLevelsDTO.getMinScore());
        assertThat(updatedScoreboardLevel.getId()).isEqualTo(scoreboardLevels.getId());
    }
}