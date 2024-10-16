package com.example.demo.services;

import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.dtos.LearningsDTO;
import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.entities.LearningTypes;
import com.example.demo.entities.Learnings;
import com.example.demo.entities.LearningTypes;
import com.example.demo.mappers.LearningTypesMapper; // Assuming you have a mapper for DTOs
import com.example.demo.repositories.LearningTypesRepository;
import com.example.demo.repositories.LearningsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningTypesService {

    private final LearningTypesRepository learningTypesRepository;
    private final LearningsRepository learningsRepository;

    private final LearningTypesMapper learningTypesMapper; // Mapper for conversion

    private final UserService userService;

    @Autowired
    public LearningTypesService(LearningTypesRepository learningTypesRepository, LearningsRepository learningsRepository, LearningTypesMapper learningTypesMapper, UserService userService) {
        this.learningTypesRepository = learningTypesRepository;
        this.learningsRepository = learningsRepository;
        this.learningTypesMapper = learningTypesMapper;
        this.userService = userService;
    }

    public LearningTypesDTO getLearningTypeById(UUID id) {
        LearningTypes learningType = learningTypesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning type not found with id " + id));
        return learningTypesMapper.toLearningTypesDTO(learningType); // Convert to DTO
    }

    public List<LearningTypesDTO> getLearningTypes() {
        List<LearningTypes> learningTypes = learningTypesRepository.findAll();
        return learningTypes.stream()
                .map(learningTypesMapper::toLearningTypesDTO)
                .collect(Collectors.toList());
    }



    public LearningTypesDTO addLearningType(LearningTypesDTO learningTypesDTO) {
        LearningTypes learningTypes = learningTypesMapper.toLearningTypes(learningTypesDTO);
        learningTypesRepository.save(learningTypes);
        userService.sendNotificationsToAll(learningTypes.getTypeName() + "learning type has been added!");

        return learningTypesMapper.toLearningTypesDTO(learningTypes);
    }

    public LearningTypesDTO updateLearningType(UUID id, LearningTypesDTO LearningTypesUpdateDTO) {

        LearningTypes scoreboardLevel = learningTypesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning type not found with ID: " + id));

        learningTypesMapper.updateLearningTypesFromDTO(LearningTypesUpdateDTO,scoreboardLevel);

        learningTypesRepository.save(scoreboardLevel);
        return learningTypesMapper.toLearningTypesDTO(scoreboardLevel);
    }

    public void deleteLearningType(UUID id) {
        if (learningTypesRepository.existsById(id)) {
            learningsRepository.deleteBylearningTypeId(id);
            learningTypesRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Learning type with id " + id + " does not exist.");
        }
    }



}
