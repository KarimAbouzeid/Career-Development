package com.example.demo.services;


import com.example.demo.dtos.ProofTypesDTO;
import com.example.demo.mappers.LearningTypesMapper;
import com.example.demo.mappers.ProofTypesMapper;
import com.example.demo.repositories.ProofTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProofTypesService {

    private final ProofTypesRepository proofTypeRepository;
    private final ProofTypesMapper proofTypesMapper; // Mapper for conversion


    @Autowired
    public ProofTypesService(ProofTypesRepository proofTypeRepository, ProofTypesMapper proofTypesMapper) {
        this.proofTypeRepository = proofTypeRepository;
        this.proofTypesMapper = proofTypesMapper;
    }

    public List<ProofTypesDTO> getAllProofTypes() {
        return proofTypeRepository.findAll().stream()
                .map(proofTypesMapper::toProofTypesDTO)
                .collect(Collectors.toList());
    }


}