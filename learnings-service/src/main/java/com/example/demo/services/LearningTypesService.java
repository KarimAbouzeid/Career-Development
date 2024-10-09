package com.example.demo.services;

import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.dtos.LearningsDTO;
import com.example.demo.entities.LearningTypes;
import com.example.demo.entities.Learnings;
import com.example.demo.mappers.LearningTypesMapper; // Assuming you have a mapper for DTOs
import com.example.demo.repositories.LearningTypesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningTypesService {

    private final LearningTypesRepository learningTypesRepository;
    private final LearningTypesMapper learningTypesMapper; // Mapper for conversion

    @Autowired
    public LearningTypesService(LearningTypesRepository learningTypesRepository, LearningTypesMapper learningTypesMapper) {
        this.learningTypesRepository = learningTypesRepository;
        this.learningTypesMapper = learningTypesMapper;
    }

    public LearningTypesDTO getLearningTypeById(UUID id) {
        LearningTypes learningType = learningTypesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LearningType not found with id " + id));
        return learningTypesMapper.toLearningTypesDTO(learningType); // Convert to DTO
    }

    public List<LearningTypesDTO> getLearningTypes() {
        List<LearningTypes> learningTypes = learningTypesRepository.findAll();
        return learningTypes.stream()
                .map(learningTypesMapper::toLearningTypesDTO)
                .collect(Collectors.toList());
    }
}
