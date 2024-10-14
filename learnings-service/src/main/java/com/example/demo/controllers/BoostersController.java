package com.example.demo.controllers;

import com.example.demo.dtos.BoostersDTO;
import com.example.demo.services.BoostersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/boosters")
public class BoostersController {

    private final BoostersService boostersService;

    @Autowired
    public BoostersController(BoostersService boostersService) {
        this.boostersService = boostersService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBooster(@RequestBody BoostersDTO boosterDTO) {
        boostersService.createBooster(boosterDTO);
        return new ResponseEntity<>("Booster created successfully.", HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BoostersDTO> getBoosterById(@PathVariable UUID id) {
        BoostersDTO booster = boostersService.getBoosterById(id);
        return new ResponseEntity<>(booster, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BoostersDTO>> getAllBoosters() {
        List<BoostersDTO> boosters = boostersService.getAllBoosters();
        return new ResponseEntity<>(boosters, HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateBooster(@PathVariable UUID id) {
        String response = boostersService.activateBooster(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateBooster(@PathVariable UUID id) {
        String response = boostersService.deactivateBooster(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
