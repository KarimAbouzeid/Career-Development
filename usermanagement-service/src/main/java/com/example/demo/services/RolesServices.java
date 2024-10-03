package com.example.demo.services;

import com.example.demo.dtos.RoleDto;
import com.example.demo.entities.Role;
import com.example.demo.mappers.RolesMapper;
import com.example.demo.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RolesServices {

    private final RoleRepository rolesRepository;
    private final RolesMapper rolesMapper;

    public RolesServices(RoleRepository rolesRepository, RolesMapper rolesMapper) {
        this.rolesRepository = rolesRepository;
        this.rolesMapper = rolesMapper;
    }

    public RoleDto addRole(RoleDto roleDto){
        Role role = this.rolesMapper.toRole(roleDto);
        this.rolesRepository.save(role);
        return this.rolesMapper.toRoleDTO(role);

    }
}
