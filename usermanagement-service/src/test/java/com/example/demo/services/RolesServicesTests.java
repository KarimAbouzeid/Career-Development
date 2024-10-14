package com.example.demo.services;

import com.example.demo.dtos.RoleDto;
import com.example.demo.entities.Role;
import com.example.demo.mappers.RolesMapper;
import com.example.demo.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesServicesTest {

    @InjectMocks
    private RolesServices rolesServices;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RolesMapper rolesMapper;

    private RoleDto roleDto;
    private Role role;

    @BeforeEach
    void setUp() {
        roleDto = new RoleDto();
        roleDto.setName("ADMIN");
        role = new Role();
        role.setName("ADMIN");
    }

    @Test
    void testAddRole() {
        // Arrange
        when(rolesMapper.toRole(roleDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(rolesMapper.toRoleDTO(role)).thenReturn(roleDto);

        // Act
        RoleDto result = rolesServices.addRole(roleDto);

        // Assert
        assertNotNull(result);
        assertEquals(roleDto.getName(), result.getName());

        // Verify that the repository save method was called
        verify(roleRepository, times(1)).save(role);
        // Verify that the mapper was called to convert Role to RoleDto
        verify(rolesMapper, times(1)).toRoleDTO(role);
    }

    // Additional tests can be added here
}
