package com.example.demo.mappers;

import com.example.demo.dtos.TitlesDTO;
import com.example.demo.entities.Titles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel ="spring")


public interface TitlesMapper {


    @Mapping(target = "departmentId", ignore = true)
    TitlesDTO toTitlesDTO(Titles titles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departmentId", ignore = true)
    Titles toTitle(TitlesDTO titlesDTO);
}




