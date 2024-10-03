package com.example.demo.mappers;

import com.example.demo.dtos.DepartmentsDTO;
import com.example.demo.dtos.RoleDto;
import com.example.demo.entities.Departments;
import com.example.demo.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel ="spring")
public interface RolesMapper {

    RoleDto toRoleDTO(Role role);

    @Mapping(target = "id", ignore = true)
    Role toRole(RoleDto roleDto);


}
