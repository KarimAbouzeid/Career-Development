package com.example.demo.controllers;

import com.example.demo.dtos.ActionsDTO;
import com.example.demo.services.ActionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/actions")
public class ActionsController {

    private final ActionsService actionsService;

    @Autowired
    public ActionsController(ActionsService actionsService) {
        this.actionsService = actionsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActionsDTO>> getAllActions() {
        List<ActionsDTO> actions = actionsService.getAllActions();
        return ResponseEntity.ok(actions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionsDTO> getActionById(@PathVariable UUID id) {
            ActionsDTO action = actionsService.getActionById(id);
            return ResponseEntity.ok(action);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAction(@RequestBody ActionsDTO actionDTO) {
        actionsService.addAction(actionDTO);
        return ResponseEntity.ok("Action added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAction(@PathVariable UUID id) {
            actionsService.deleteAction(id);
            return ResponseEntity.ok("Action deleted successfully");
    }
}
