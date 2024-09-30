package com.example.demo.repositories;

import com.example.demo.entities.ScoreboardLevels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScoreboardLevelsRepository extends JpaRepository<ScoreboardLevels, UUID> {
}
