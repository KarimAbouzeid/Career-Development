package com.example.demo.services;

import com.example.demo.dtos.LearningSubjectsDTO;
import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.entities.LearningSubjects;
import com.example.demo.entities.LearningTypes;
import com.example.demo.mappers.LearningSubjectsMapper;
import com.example.demo.repositories.LearningSubjectsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningSubjectsService {

    private final LearningSubjectsRepository learningSubjectsRepository;
    private final LearningSubjectsMapper learningSubjectsMapper;

    @Autowired
    public LearningSubjectsService(LearningSubjectsRepository learningSubjectsRepository, LearningSubjectsMapper learningSubjectsMapper) {
        this.learningSubjectsRepository = learningSubjectsRepository;
        this.learningSubjectsMapper = learningSubjectsMapper;
    }

    public LearningSubjectsDTO getLearningSubjectById(UUID id) {
        LearningSubjects learningSubject = learningSubjectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LearningSubject not found with id " + id));
        return learningSubjectsMapper.toLearningSubjectsDTO(learningSubject);
    }


    public List<LearningSubjectsDTO> getLearningSubjects() {
        List<LearningSubjects> learningSubjects = learningSubjectsRepository.findAll();
        return learningSubjects.stream()
                .map(learningSubjectsMapper::toLearningSubjectsDTO)
                .collect(Collectors.toList());
    }
}

