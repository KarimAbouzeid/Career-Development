package com.example.demo.controllers;

import com.example.demo.dtos.LearningSubjectsDTO;
import com.example.demo.services.LearningSubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/learningSubjects")
public class LearningSubjectsController {

    private final LearningSubjectsService learningSubjectsService;

    @Autowired
    public LearningSubjectsController(LearningSubjectsService learningSubjectsService) {
        this.learningSubjectsService = learningSubjectsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningSubjectsDTO> getLearningSubjectById(@PathVariable UUID id) {
        LearningSubjectsDTO learningSubjectDTO = learningSubjectsService.getLearningSubjectById(id);
        return new ResponseEntity<>(learningSubjectDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LearningSubjectsDTO>> getAllLearningSubjects() {
        List<LearningSubjectsDTO> learningSubjects = learningSubjectsService.getLearningSubjects();
        return new ResponseEntity<>(learningSubjects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addlearningSubject(@RequestBody LearningSubjectsDTO learningSubjectsDTO) {

        learningSubjectsService.addLearningSubject(learningSubjectsDTO);
        return ResponseEntity.ok("Learning subject added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatelearningSubjects(@PathVariable UUID id, @RequestBody LearningSubjectsDTO learningSubjectsDTO) {

        learningSubjectsService.updateLearningSubject(id, learningSubjectsDTO);
        return ResponseEntity.ok("Learning subject updated successfully");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletelearningSubject(@PathVariable UUID id) {
        learningSubjectsService.deleteLearningSubject(id);
        return ResponseEntity.ok("Learning subject deleted successfully");
    }
}
