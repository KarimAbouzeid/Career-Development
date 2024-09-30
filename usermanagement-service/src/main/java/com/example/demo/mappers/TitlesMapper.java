package com.example.demo.mappers;

import com.example.demo.dtos.TitlesDTO;
import com.example.demo.entities.Titles;
import org.mapstruct.*;


@Mapper(componentModel ="spring")
public interface TitlesMapper {


    @Mapping(target = "departmentId", ignore = true)
    TitlesDTO toTitlesDTO(Titles titles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departmentId", ignore = true)
    Titles toTitle(TitlesDTO titlesDTO);

    @Mapping(target = "departmentId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTitlesFromDTO(TitlesDTO titlesDTO, @MappingTarget Titles entity);
}




