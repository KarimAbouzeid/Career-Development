package com.example.demo.controllers;


import com.example.demo.dtos.ProofTypesDTO;
import com.example.demo.services.ProofTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/proofTypes")
public class ProofTypesController {

    private final ProofTypesService proofTypeService;

    @Autowired
    public ProofTypesController(ProofTypesService proofTypeService) {
        this.proofTypeService = proofTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ProofTypesDTO>> getAllProofTypes() {
        List<ProofTypesDTO> proofTypes = proofTypeService.getAllProofTypes();
        return new ResponseEntity<>(proofTypes, HttpStatus.OK);
    }
}
