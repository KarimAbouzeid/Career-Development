package com.example.demo.mappers;

import com.example.demo.dtos.LearningSubjectsDTO;
import com.example.demo.entities.LearningSubjects;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LearningSubjectsMapper {

    LearningSubjectsDTO toLearningSubjectsDTO(LearningSubjects learningSubjects);

    LearningSubjects toLearningSubjects(LearningSubjectsDTO learningSubjectsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLearningSubjectsFromDTO(LearningSubjectsDTO learningSubjectsDTO, @MappingTarget LearningSubjects entity);
}
