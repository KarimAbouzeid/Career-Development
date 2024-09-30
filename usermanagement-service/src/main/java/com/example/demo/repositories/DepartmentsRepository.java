package com.example.demo.repositories;

import com.example.demo.entities.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentsRepository extends JpaRepository<Departments, UUID> {
}
