package com.example.demo.mappers;

import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.entities.UserScores;
import org.mapstruct.*;


@Mapper(componentModel ="spring")
public interface UserScoresMapper {

    @Mapping(source = "userId", target = "userId")
    UserScoresDTO toUserScoresDTO(UserScores userScores);

    @Mapping(source = "userId", target = "userId")
    UserScores toUserScores(UserScoresDTO userScoresDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserScoresFromDTO(UserScoresDTO userScoresDTO, @MappingTarget UserScores userScores);
}



