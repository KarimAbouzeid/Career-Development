package com.example.demo.controllers;

import com.example.demo.dtos.LearningsDTO;
import com.example.demo.entities.Learnings;
import com.example.demo.services.LearningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/learnings")
public class LearningsController {

    private final LearningsService learningsService;

    @Autowired
    public LearningsController(LearningsService learningsService) {
        this.learningsService = learningsService;
    }

    @GetMapping
    public ResponseEntity<List<LearningsDTO>> getAllLearnings() {
        List<LearningsDTO> learnings = learningsService.getAllLearnings();
        System.out.println(learnings);
        return new ResponseEntity<>(learnings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningsDTO> getLearningById(@PathVariable UUID id) {
        LearningsDTO learning = learningsService.getLearningById(id);
        return new ResponseEntity<>(learning, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLearning(@RequestBody LearningsDTO learningsDTO) {
        learningsService.addLearning(learningsDTO);
        return new ResponseEntity<>("Learning added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLearning(@PathVariable UUID id, @RequestBody LearningsDTO learningsDTO) {
        learningsService.updateLearning(id, learningsDTO);
        return new ResponseEntity<>("Learning updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLearning(@PathVariable UUID id) {
        learningsService.deleteLearning(id);
        return new ResponseEntity<>("Learning deleted successfully", HttpStatus.OK);
        }

}
