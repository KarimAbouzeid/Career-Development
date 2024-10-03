package com.example.demo.repositories;

import com.example.demo.entities.Departments;
import com.example.demo.entities.Titles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TitlesRepository extends JpaRepository<Titles, UUID> {
    List<Titles> findByDepartmentId(Departments department);

}
