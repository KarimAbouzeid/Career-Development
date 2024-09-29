package com.example.demo.mappers.UsersDB;

import com.example.demo.dtos.usersDB.DepartmentsDTO;
import com.example.demo.entities.UsersDB.Departments;
import com.example.demo.mappers.usersDB.DepartmentsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DepartmentsMapperTests {

    @Autowired
    private DepartmentsMapper departmentsMapper;

    private Departments departments;
    private DepartmentsDTO departmentsDTO;

    @BeforeEach
    public void setUp() {
        departments = new Departments();
        departments.setId(UUID.randomUUID());
        departments.setName("Engineering");

        departmentsDTO = new DepartmentsDTO();
        departmentsDTO.setName("Marketing");
    }

    @Test
    public void toDepartmentsDTO_ShouldMapDepartmentsToDepartmentsDTO() {
        DepartmentsDTO result = departmentsMapper.toDepartmentsDTO(departments);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(departments.getName());
    }

    @Test
    public void toDepartments_ShouldMapDepartmentsDTOToDepartments() {
        Departments result = departmentsMapper.toDepartments(departmentsDTO);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(departmentsDTO.getName());
    }

    @Test
    public void updateDepartmentsFromDTO_ShouldUpdateDepartmentsWithDepartmentsDTO() {
        departments.setName("Old Department");
        departmentsMapper.updateDepartmentsFromDTO(departmentsDTO, departments);
        assertThat(departments).isNotNull();
        assertThat(departments.getName()).isEqualTo(departmentsDTO.getName());
    }
}
