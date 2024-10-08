package com.example.demo.mappers;

import com.example.demo.dtos.LearningsDTO;
import com.example.demo.entities.Learnings;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LearningsMapper {

    LearningsDTO toLearningsDTO(Learnings learnings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "learningType.id", source = "learningTypeId")
    @Mapping(target = "learningSubject.id", source = "learningSubjectId")
    Learnings toLearnings(LearningsDTO learningsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLearningsFromDTO(LearningsDTO learningsDTO, @MappingTarget Learnings entity);
}
