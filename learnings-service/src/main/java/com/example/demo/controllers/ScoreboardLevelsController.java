package com.example.demo.controllers;

import com.example.demo.dtos.ScoreboardLevelsDTO;
import com.example.demo.services.ScoreboardLevelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*",allowCredentials = "true")
@RestController
@RequestMapping("/api/scoreboardLevels")
public class ScoreboardLevelsController {

    private final ScoreboardLevelsService scoreboardLevelsService;

    @Autowired
    public ScoreboardLevelsController(ScoreboardLevelsService scoreboardLevelsService) {
        this.scoreboardLevelsService = scoreboardLevelsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreboardLevelsDTO> getScoreboard(@PathVariable UUID id) {

        ScoreboardLevelsDTO scoreboardLevelsDTO = scoreboardLevelsService.getScoreboardLevel(id);
        return ResponseEntity.ok(scoreboardLevelsDTO);
    }

    @PostMapping
    public ResponseEntity<ScoreboardLevelsDTO> addScoreboardLevel(@RequestBody ScoreboardLevelsDTO scoreboardLevelsDTO) {

        ScoreboardLevelsDTO returnedScoreboardLevelsDTO = scoreboardLevelsService.addScoreboardLevel(scoreboardLevelsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedScoreboardLevelsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreboardLevelsDTO> updateScoreboardLevel(@PathVariable UUID id, @RequestBody ScoreboardLevelsDTO scoreboardLevelsDTO) {

        ScoreboardLevelsDTO returnedScoreboardLevelsDTO = scoreboardLevelsService.updateScoreboardLevel(id, scoreboardLevelsDTO);
        return ResponseEntity.ok(returnedScoreboardLevelsDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScoreboardLevel(@PathVariable UUID id) {
        scoreboardLevelsService.deletescoreboardLevel(id);
        return ResponseEntity.ok("ScoreboardLevel deleted successfully");

    }

    @GetMapping("/level")
    public String getLevelByScore(@RequestParam int score) {
        return scoreboardLevelsService.getLevelByScore(score);
    }
}
