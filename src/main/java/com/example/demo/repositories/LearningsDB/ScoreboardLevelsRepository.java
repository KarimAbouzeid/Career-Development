package com.example.demo.repositories.LearningsDB;

import com.example.demo.entities.learningsDB.ScoreboardLevels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScoreboardLevelsRepository extends JpaRepository<ScoreboardLevels, UUID> {
}
