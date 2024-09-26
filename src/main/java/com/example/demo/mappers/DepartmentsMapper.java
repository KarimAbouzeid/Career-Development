package com.example.demo.mappers;

import com.example.demo.dtos.DepartmentsDTO;
import com.example.demo.entities.Departments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel ="spring")
public interface DepartmentsMapper {
    DepartmentsDTO toDepartmentsDTO(Departments departments);

    @Mapping(target = "id", ignore = true)
    Departments toDepartments(DepartmentsDTO departmentsDTO);
}









