package com.example.demo.mappers;

import com.example.demo.dtos.LearningsDTO;
import com.example.demo.entities.Learnings;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LearningsMapper {

    @Mapping(source = "learningType.id", target = "learningTypeId")
    @Mapping(source = "learningSubject.id", target = "learningSubjectId")
    LearningsDTO toLearningsDTO(Learnings learnings);

    @Mapping(source = "learningTypeId", target = "learningType.id")
    @Mapping(source = "learningSubjectId", target = "learningSubject.id")
    Learnings toLearnings(LearningsDTO learningsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLearningsFromDTO(LearningsDTO learningsDTO, @MappingTarget Learnings entity);

}
