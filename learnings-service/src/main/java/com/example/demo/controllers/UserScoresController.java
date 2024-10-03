package com.example.demo.controllers;

import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.services.UserScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/userScores")
public class UserScoresController {

    private final UserScoresService userScoresService;

    @Autowired
    public UserScoresController(UserScoresService userScoresService) {
        this.userScoresService = userScoresService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserScoresDTO> getUserScore(@PathVariable UUID id) {
        UserScoresDTO userScore = userScoresService.getUserScore(id);
        return new ResponseEntity<>(userScore, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserScoresDTO> addUserScore( @RequestBody UserScoresDTO userScoresDTO) {
        UserScoresDTO createdUserScore = userScoresService.addUserScore( userScoresDTO);
        return new ResponseEntity<>(createdUserScore, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserScoresDTO> updateUserScore( @RequestBody UserScoresDTO userScoresDTO) {
        UserScoresDTO updatedUserScore = userScoresService.updateUserScore( userScoresDTO);
        return new ResponseEntity<>(updatedUserScore, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserScore(@PathVariable UUID id) {
        userScoresService.deleteUserScore(id);
        return ResponseEntity.ok("User score deleted successfully");
    }

    @GetMapping
    public ResponseEntity<Page<UserScoresDTO>> getAllUserScores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserScoresDTO> userScores = userScoresService.getAllUserScores(pageable);
        return new ResponseEntity<>(userScores, HttpStatus.OK);
    }

}