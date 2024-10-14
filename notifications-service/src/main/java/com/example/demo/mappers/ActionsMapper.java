package com.example.demo.mappers;

import com.example.demo.dtos.ActionsDTO;
import com.example.demo.entities.Actions;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ActionsMapper {

    ActionsDTO toActionsDTO(Actions actions);

    Actions toActions(ActionsDTO actionsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateActionsFromDTO(ActionsDTO actionsDTO, @MappingTarget Actions actions);
}
