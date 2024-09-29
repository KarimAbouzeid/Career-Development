package com.example.demo.repositories.LearningsDB;

import com.example.demo.entities.LearningsDB.UserScores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserScoresRepository extends JpaRepository<UserScores, UUID> {
}
