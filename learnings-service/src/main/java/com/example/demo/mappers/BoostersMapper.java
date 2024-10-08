package com.example.demo.mappers;

import com.example.demo.dtos.BoostersDTO;
import com.example.demo.entities.Boosters;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BoostersMapper {

    BoostersDTO toBoostersDTO(Boosters boosters);

    Boosters toBoosters(BoostersDTO boostersDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBoostersFromDTO(BoostersDTO boostersDTO, @MappingTarget Boosters entity);
}
