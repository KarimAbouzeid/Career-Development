package com.example.demo.mappers;

import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.entities.UserScores;
import org.mapstruct.*;


@Mapper(componentModel ="spring")
public interface UserScoresMapper {

    UserScoresDTO toUserScoresDTO(UserScores userScores);

    UserScores toUserScores(UserScoresDTO userScoresDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserScoresFromDTO(UserScoresDTO userScoresDTO, @MappingTarget UserScores userScores);
}



