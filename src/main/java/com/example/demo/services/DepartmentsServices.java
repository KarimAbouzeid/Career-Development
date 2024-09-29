package com.example.demo.services;

import com.example.demo.dtos.DepartmentsDTO;
import com.example.demo.entities.UsersDB.Departments;
import com.example.demo.mappers.DepartmentsMapper;
import com.example.demo.repositories.UsersDB.DepartmentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DepartmentsServices {

    private final DepartmentsRepository departmentsRepository;
    private final DepartmentsMapper departmentsMapper;

    @Autowired
    public DepartmentsServices(DepartmentsRepository departmentsRepository, DepartmentsMapper departmentsMapper) {
        this.departmentsRepository = departmentsRepository;
        this.departmentsMapper = departmentsMapper;

    }

    public DepartmentsDTO getDepartment(UUID id) {
        Departments department = departmentsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Department with id " + id + "not found"));
        return departmentsMapper.toDepartmentsDTO(department);
    }

    public DepartmentsDTO addDepartment(DepartmentsDTO departmentsDTO) {
        Departments department = departmentsMapper.toDepartments(departmentsDTO);
        departmentsRepository.save(department);
        return departmentsMapper.toDepartmentsDTO(department);
    }

    public DepartmentsDTO updateDepartment(UUID id, DepartmentsDTO departmentsDTO) {

        Departments department = departmentsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Department with id " + id + "not found"));
        departmentsMapper.updateDepartmentsFromDTO(departmentsDTO, department);
        departmentsRepository.save(department);
        return departmentsMapper.toDepartmentsDTO(department);
    }


    public void deleteDepartment(UUID id) {
        if (departmentsRepository.existsById(id))
            departmentsRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Department with id " + id + " does not exist.");
    }






}
