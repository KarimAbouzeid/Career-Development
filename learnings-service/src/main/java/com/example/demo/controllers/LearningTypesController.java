package com.example.demo.controllers;

import com.example.demo.dtos.LearningTypesDTO;
import com.example.demo.dtos.LearningsDTO;
import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.services.LearningTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/learningTypes")
public class LearningTypesController {

    private final LearningTypesService learningTypesService;

    @Autowired
    public LearningTypesController(LearningTypesService learningTypesService) {
        this.learningTypesService = learningTypesService;
    }

    @GetMapping
    public ResponseEntity<List<LearningTypesDTO>> getAllLearningTypes() {
        List<LearningTypesDTO> learningTypes = learningTypesService.getLearningTypes();
        return new ResponseEntity<>(learningTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningTypesDTO> getLearningTypeById(@PathVariable UUID id) {
        LearningTypesDTO learningTypeDTO = learningTypesService.getLearningTypeById(id);
        return new ResponseEntity<>(learningTypeDTO, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> addLearningType(@RequestBody LearningTypesDTO learningTypesDTO) {

        learningTypesService.addLearningType(learningTypesDTO);
        return ResponseEntity.ok("learning type added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLearningTypes(@PathVariable UUID id, @RequestBody LearningTypesDTO learningTypesDTO) {

        learningTypesService.updateLearningType(id, learningTypesDTO);
        return ResponseEntity.ok("learning type updated successfully");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLearningType(@PathVariable UUID id) {
        learningTypesService.deleteLearningType(id);
        return ResponseEntity.ok("Learning type deleted successfully");
    }
}
