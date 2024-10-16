package com.example.demo.services;

import com.example.demo.dtos.LearningSubjectsDTO;
import com.example.demo.entities.LearningSubjects;
import com.example.demo.mappers.LearningSubjectsMapper;
import com.example.demo.repositories.LearningSubjectsRepository;
import com.example.demo.repositories.LearningsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningSubjectsService {

    private final LearningSubjectsRepository learningSubjectsRepository;
    private final LearningsRepository learningsRepository;

    private final LearningSubjectsMapper learningSubjectsMapper;

    private final UserService userService;


    @Autowired
    public LearningSubjectsService(LearningSubjectsRepository learningSubjectsRepository, LearningsRepository learningsRepository, LearningSubjectsMapper learningSubjectsMapper, UserService userService) {
        this.learningSubjectsRepository = learningSubjectsRepository;
        this.learningsRepository = learningsRepository;
        this.learningSubjectsMapper = learningSubjectsMapper;
        this.userService = userService;
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


    public LearningSubjectsDTO addLearningSubject(LearningSubjectsDTO learningSubjectsDTO) {
        LearningSubjects learningSubjects = learningSubjectsMapper.toLearningSubjects(learningSubjectsDTO);
        learningSubjectsRepository.save(learningSubjects);
        userService.sendNotificationsToAll(learningSubjects.getSubject() + "learning subject has been added!");

        return learningSubjectsMapper.toLearningSubjectsDTO(learningSubjects);
    }

    public LearningSubjectsDTO updateLearningSubject(UUID id, LearningSubjectsDTO learningSubjectsUpdateDTO) {

        LearningSubjects scoreboardLevel = learningSubjectsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning subject not found with ID: " + id));

        learningSubjectsMapper.updateLearningSubjectsFromDTO(learningSubjectsUpdateDTO,scoreboardLevel);

        learningSubjectsRepository.save(scoreboardLevel);
        return learningSubjectsMapper.toLearningSubjectsDTO(scoreboardLevel);
    }

    public void deleteLearningSubject(UUID id) {
        if (learningSubjectsRepository.existsById(id)) {
            learningsRepository.deleteByLearningSubjectId(id);
            learningSubjectsRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Learning subject with id " + id + " does not exist.");
        }
    }
}

