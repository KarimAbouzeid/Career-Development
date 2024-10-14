package com.example.demo.repositories;

import com.example.demo.entities.Learnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LearningsRepository extends JpaRepository<Learnings, UUID> {
    void deleteBylearningTypeId(@Param("learningTypeId") UUID learningTypeId);
    void deleteByLearningSubjectId(@Param("learningSubjectId") UUID learningSubjectId);


}
