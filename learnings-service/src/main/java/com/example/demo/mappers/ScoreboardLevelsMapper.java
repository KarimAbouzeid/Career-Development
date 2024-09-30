package com.example.demo.mappers;

import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.entities.ScoreboardLevels;
import org.mapstruct.*;

@Mapper(componentModel ="spring")

public interface ScoreboardLevelsMapper {

    ScoreboardLevelsDTO toScoreboardLevelsDTO(ScoreboardLevels scoreboardLevels);

    @Mapping(target = "id", ignore = true)
    ScoreboardLevels toScoreboardLevels(ScoreboardLevelsDTO scoreboardLevelsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateScoreboardLevelsFromDTO(ScoreboardLevelsDTO scoreboardLevelsDTO, @MappingTarget ScoreboardLevels entity);
    
}
