package com.example.demo.repositories.UsersDB;

import com.example.demo.entities.UsersDB.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentsRepository extends JpaRepository<Departments, UUID> {
}
