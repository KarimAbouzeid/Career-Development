package com.example.demo.mappers.LearningsDB;

import com.example.demo.dtos.learningsDB.UserScoresDTO;
import com.example.demo.entities.LearningsDB.UserScores;
import org.mapstruct.*;


@Mapper(componentModel ="spring")
public interface UserScoresMapper {

    UserScoresDTO toUserScoresDTO(UserScores userScores);

    UserScores toUserScores(UserScoresDTO userScoresDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserScoresFromDTO(UserScoresDTO userScoresDTO, @MappingTarget UserScores userScores);
}




