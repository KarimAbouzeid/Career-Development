package com.example.demo.mappers.UsersDB;

import com.example.demo.dtos.usersDB.TitlesDTO;
import com.example.demo.entities.UsersDB.Titles;
import com.example.demo.mappers.usersDB.TitlesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TitlesMapperTests {

    @Autowired
    private TitlesMapper titlesMapper;

    private Titles titles;
    private TitlesDTO titlesDTO;

    @BeforeEach
    public void setUp() {
        titles = new Titles();
        titles.setId(UUID.randomUUID());
        titles.setTitle("Software Engineer");

        titlesDTO = new TitlesDTO();
        titlesDTO.setTitle("Senior Software Engineer");
    }

    @Test
    public void toTitlesDTO_ShouldMapTitlesToTitlesDTO() {
        
        TitlesDTO result = titlesMapper.toTitlesDTO(titles);

        
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(titles.getTitle());
    }

    @Test
    public void toTitle_ShouldMapTitlesDTOToTitles() {
        
        Titles result = titlesMapper.toTitle(titlesDTO);

        
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(titlesDTO.getTitle());
    }

    @Test
    public void updateTitlesFromDTO_ShouldUpdateTitlesWithTitlesDTO() {
        titles.setTitle("Old Title");

        
        titlesMapper.updateTitlesFromDTO(titlesDTO, titles);

        
        assertThat(titles).isNotNull();
        assertThat(titles.getTitle()).isEqualTo(titlesDTO.getTitle());
    }
}
