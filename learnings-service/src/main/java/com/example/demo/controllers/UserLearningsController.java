package com.example.demo.controllers;

import com.example.demo.dtos.SubmitUserLearningDTO;
import com.example.demo.dtos.UserLearningsDTO;
import com.example.demo.services.UserLearningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/userLearnings")
public class UserLearningsController {

    private final UserLearningsService userLearningsService;

    @Autowired
    public UserLearningsController(UserLearningsService userLearningsService) {
        this.userLearningsService = userLearningsService;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitUserLearning(@RequestBody SubmitUserLearningDTO dto) {
        userLearningsService.submitUserLearning(dto);
        return new ResponseEntity<>("Learning submitted successfully", HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserLearningsDTO>> getSubmittedLearningsByUser(@PathVariable UUID userId) {
        List<UserLearningsDTO> submittedLearnings = userLearningsService.getSubmittedLearningsByUser(userId);
        return new ResponseEntity<>(submittedLearnings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLearningsDTO> getSubmittedLearningById(@PathVariable UUID id) {
        UserLearningsDTO userLearning = userLearningsService.getSubmittedLearningById(id);
        return new ResponseEntity<>(userLearning, HttpStatus.OK);
    }

    @PutMapping("/updateApprovalStatus/{id}")
    public ResponseEntity<String> updateApprovalStatus(@PathVariable UUID id, @RequestBody String newStatus) {
        userLearningsService.updateApprovalStatus(id, newStatus);
        return new ResponseEntity<>("Approval status updated successfully", HttpStatus.OK);
    }
}
