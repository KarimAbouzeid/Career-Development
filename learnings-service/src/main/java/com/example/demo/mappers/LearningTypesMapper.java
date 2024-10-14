package com.example.demo.mappers;

import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.entities.LearningTypes;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LearningTypesMapper {

    LearningTypesDTO toLearningTypesDTO(LearningTypes learningTypes);

    LearningTypes toLearningTypes(LearningTypesDTO learningTypesDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLearningTypesFromDTO(LearningTypesDTO learningTypesDTO, @MappingTarget LearningTypes entity);
}
