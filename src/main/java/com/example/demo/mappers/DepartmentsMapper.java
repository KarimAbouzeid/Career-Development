package com.example.demo.mappers;

import com.example.demo.dtos.DepartmentsDTO;
import com.example.demo.entities.Departments;
import org.mapstruct.*;

@Mapper(componentModel ="spring")
public interface DepartmentsMapper {
    DepartmentsDTO toDepartmentsDTO(Departments departments);

    @Mapping(target = "id", ignore = true)
    Departments toDepartments(DepartmentsDTO departmentsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentsFromDTO(DepartmentsDTO departmentsDTO, @MappingTarget Departments entity);
}









