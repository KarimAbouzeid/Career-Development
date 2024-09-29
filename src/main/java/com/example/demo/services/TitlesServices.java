package com.example.demo.services;

import com.example.demo.dtos.TitlesDTO;
import com.example.demo.entities.UsersDB.Departments;
import com.example.demo.entities.UsersDB.Titles;
import com.example.demo.mappers.TitlesMapper;
import com.example.demo.repositories.UsersDB.DepartmentsRepository;
import com.example.demo.repositories.UsersDB.TitlesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TitlesServices {

    private final TitlesRepository titlesRepository;
    private final DepartmentsRepository departmentsRepository;
    private final TitlesMapper titlesMapper;

    @Autowired
    public TitlesServices(TitlesRepository titlesRepository, DepartmentsRepository departmentsRepository, TitlesMapper titlesMapper) {
        this.titlesRepository = titlesRepository;
        this.departmentsRepository = departmentsRepository;
        this.titlesMapper = titlesMapper;
    }

    public TitlesDTO getTitles(UUID id) {
        Titles title = this.titlesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Title with id " + id + " not found"));
        return titlesMapper.toTitlesDTO(title);
    }

    public TitlesDTO addTitles(TitlesDTO titlesDTO) {

        Titles title = titlesMapper.toTitle(titlesDTO);
        Map<String, Object> map = this.getDepartment(titlesDTO);

        title.setDepartmentId((Departments) map.get("departments"));
        this.titlesRepository.save(title);
        return titlesMapper.toTitlesDTO(title);
    }

    public TitlesDTO updateTitles(UUID id, TitlesDTO titlesDTO) {

        Titles tobeUpdatedTitle = this.titlesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Title with id " + id + " not found"));
        Map<String, Object> map = this.getDepartment(titlesDTO);
        Departments departmentId = (Departments) map.get("departments");

        titlesMapper.updateTitlesFromDTO(titlesDTO, tobeUpdatedTitle);
        if(departmentId != null) {
            tobeUpdatedTitle.setDepartmentId(departmentId);
        }
        this.titlesRepository.save(tobeUpdatedTitle);
        return titlesMapper.toTitlesDTO(tobeUpdatedTitle);

    }

    public void deleteTitles (UUID id) {
        if (titlesRepository.existsById(id))
            titlesRepository.deleteById(id);
         else
            throw new EntityNotFoundException("Title with id " + id + " does not exist.");

    }

    public Map<String, Object> getDepartment(TitlesDTO titlesDTO) {

        Map<String, Object> result = new HashMap<>();
        result.put("departments", null);


        if(titlesDTO.getDepartmentId() == null)
            return result;



        UUID departmentId = titlesDTO.getDepartmentId();

        Departments department = departmentsRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFoundException("Department with id " + departmentId + " not found")
        );
        result.put("departments", department);

        return result;

    }
}
