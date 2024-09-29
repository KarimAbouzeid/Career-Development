package com.example.demo.mappers.LearningsDB;

import com.example.demo.dtos.learningsDB.UserScoresDTO;
import com.example.demo.entities.LearningsDB.UserScores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserScoresMapperTests {

    @Autowired
    private UserScoresMapper userScoresMapper;

    private UserScores userScores;
    private UserScoresDTO userScoresDTO;

    @BeforeEach
    public void setUp() {
        UUID userScoreId = UUID.randomUUID();

        userScores = new UserScores();
        userScores.setUserId(userScoreId);
        userScores.setScore(85);

        userScoresDTO = new UserScoresDTO();
        userScoresDTO.setScore(85);

    }

    @Test
    public void toUserScoresDTO_validUserScores_returnsUserScoresDTO() {
        UserScoresDTO dto = userScoresMapper.toUserScoresDTO(userScores);

        assertThat(dto).isNotNull();
        assertThat(dto.getScore()).isEqualTo(userScores.getScore());
    }

    @Test
    public void toUserScores_validUserScoresDTO_returnsUserScores() {
        UserScores entity = userScoresMapper.toUserScores(userScoresDTO);

        assertThat(entity).isNotNull();
        assertThat(entity.getScore()).isEqualTo(userScoresDTO.getScore());
    }

    @Test
    public void updateUserScoresFromDTO_validUserScoresDTO_updatesUserScores() {
        UserScores updatedUserScores = new UserScores();
        updatedUserScores.setUserId(userScores.getUserId());
        updatedUserScores.setScore(90);

        userScoresMapper.updateUserScoresFromDTO(userScoresDTO, updatedUserScores);

        assertThat(updatedUserScores.getScore()).isEqualTo(userScoresDTO.getScore());
        assertThat(updatedUserScores.getUserId()).isEqualTo(userScores.getUserId());
    }
}
