package com.example.demo.mappers;

import com.example.demo.dtos.UserLearningsDTO;
import com.example.demo.entities.UserLearnings;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserLearningsMapper {

    UserLearningsDTO toUserLearningsDTO(UserLearnings userLearnings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "learning.id", source = "learningId")
    @Mapping(target = "proofType.id", source = "proofTypeId")
    @Mapping(target = "activeBooster.id", source = "activeBoosterId")
    UserLearnings toUserLearnings(UserLearningsDTO userLearningsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserLearningsFromDTO(UserLearningsDTO userLearningsDTO, @MappingTarget UserLearnings entity);
}
