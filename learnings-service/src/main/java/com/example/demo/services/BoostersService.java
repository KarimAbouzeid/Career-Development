package com.example.demo.services;

import com.example.demo.dtos.BoostersDTO;
import com.example.demo.entities.Boosters;
import com.example.demo.mappers.BoostersMapper; // Assuming you have a mapper for DTOs
import com.example.demo.repositories.BoostersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BoostersService {

    private final BoostersRepository boostersRepository;
    private final BoostersMapper boostersMapper;

    @Autowired
    public BoostersService(BoostersRepository boostersRepository, BoostersMapper boostersMapper) {
        this.boostersRepository = boostersRepository;
        this.boostersMapper = boostersMapper;
    }

    public void createBooster(BoostersDTO boosterDTO) {
        Boosters booster = boostersMapper.toBoosters(boosterDTO);
        Boosters savedBooster = boostersRepository.save(booster);
        boostersMapper.toBoostersDTO(savedBooster);
    }

    public BoostersDTO getBoosterById(UUID id) {
        Boosters booster = boostersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booster not found with id " + id));
        return boostersMapper.toBoostersDTO(booster);
    }

    public List<BoostersDTO> getAllBoosters() {
        List<Boosters> boosters = boostersRepository.findAll();
        return boosters.stream()
                .map(boostersMapper::toBoostersDTO)
                .collect(Collectors.toList());
    }

    public String activateBooster(UUID id) {
        BoostersDTO boostersDTO = getBoosterById(id);
        boostersDTO.setActive(true);
        Boosters booster = boostersMapper.toBoosters(boostersDTO);
        boostersRepository.save(booster);
        return "Booster activated successfully.";
    }

    public String deactivateBooster(UUID id) {
        BoostersDTO boostersDTO = getBoosterById(id);
        boostersDTO.setActive(false);
        Boosters booster = boostersMapper.toBoosters(boostersDTO);
        boostersRepository.save(booster);
        return "Booster deactivated successfully.";
    }
}
