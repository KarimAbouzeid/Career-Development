package com.example.demo.controllers;

import com.example.demo.dtos.TitlesDTO;
import com.example.demo.entities.Titles;
import com.example.demo.services.TitlesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/titles")
public class TitlesController {

    private final TitlesServices titlesServices;

    @Autowired
    public TitlesController(TitlesServices titlesServices) {
        this.titlesServices = titlesServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TitlesDTO> getTitle(@PathVariable UUID id) {
            TitlesDTO titleDTO = titlesServices.getTitles(id);
            return ResponseEntity.ok(titleDTO);
    }

    @PostMapping
    public ResponseEntity<TitlesDTO> addTitle(@RequestBody TitlesDTO titlesDTO) {
            TitlesDTO newTitle = titlesServices.addTitles(titlesDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTitle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TitlesDTO> updateTitle(@PathVariable UUID id, @RequestBody TitlesDTO titlesDTO) {
            TitlesDTO updatedTitle = titlesServices.updateTitles(id, titlesDTO);
            return ResponseEntity.ok(updatedTitle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTitle(@PathVariable UUID id) {
            titlesServices.deleteTitles(id);
        return ResponseEntity.ok("Title deleted successfully");

    }

    @GetMapping("/getByDepartment/{id}")
    public ResponseEntity<List<TitlesDTO>> getTitlesByDepartment(@PathVariable UUID id) {
        List<TitlesDTO> titles = titlesServices.getTitlesByDepartment(id);
        return ResponseEntity.ok(titles);
    }
}
