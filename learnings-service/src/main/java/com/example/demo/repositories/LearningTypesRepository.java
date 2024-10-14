package com.example.demo.repositories;

import com.example.demo.entities.LearningTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LearningTypesRepository extends JpaRepository<LearningTypes, UUID> {
}
