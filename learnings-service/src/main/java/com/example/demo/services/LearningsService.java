package com.example.demo.services;

import com.example.demo.dtos.LearningsDTO;
import com.example.demo.entities.Learnings;
import com.example.demo.mappers.LearningsMapper;
import com.example.demo.mappers.UserLearningsMapper;
import com.example.demo.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningsService {

    private final LearningsRepository learningsRepository;
    private final LearningsMapper learningsMapper;

    @Autowired
    public LearningsService(LearningsMapper learningsMapper, LearningsRepository learningsRepository) {
        this.learningsMapper = learningsMapper;
        this.learningsRepository = learningsRepository;

    }

    public List<LearningsDTO> getAllLearnings() {
        List<Learnings> learnings = learningsRepository.findAll();
        return learnings.stream()
                .map(learningsMapper::toLearningsDTO)
                .collect(Collectors.toList());
    }

    public LearningsDTO getLearningById(UUID id) {
        Learnings learning = learningsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning not found with id " + id));
        return learningsMapper.toLearningsDTO(learning);
    }

    //for admin
    public void addLearning(LearningsDTO learningsDTO) {
        Learnings learning = learningsMapper.toLearnings(learningsDTO);
        Learnings savedLearning = learningsRepository.save(learning);
        learningsMapper.toLearningsDTO(savedLearning);
    }

    //for admin
    public void updateLearning(UUID id, LearningsDTO learningsDTO) {

        Learnings learning = learningsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Learning not found with ID: " + id));


        learningsMapper.updateLearningsFromDTO(learningsDTO,learning);

        learningsRepository.save(learning);
        learningsMapper.toLearningsDTO(learning);
    }
}
