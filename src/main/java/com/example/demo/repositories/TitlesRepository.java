package com.example.demo.repositories;

import com.example.demo.entities.UsersDB.Titles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TitlesRepository extends JpaRepository<Titles, UUID> {
}
