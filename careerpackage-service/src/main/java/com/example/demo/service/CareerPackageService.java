package com.example.demo.service;

import com.example.demo.dtos.CareerPackageRequestDto;
import com.example.demo.dtos.CareerPackageResponseDto;
import com.example.demo.enums.Title;
import com.example.demo.mappers.CareerPackageMapper;
import com.example.demo.entities.CareerPackage;
import com.example.demo.repository.CareerPackageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CareerPackageService {

    @Autowired
    private  CareerPackageRepository careerPackageRepository;

    @Autowired
    private  CareerPackageMapper careerPackageMapper;


    public CareerPackageResponseDto addCareerPackage(CareerPackageRequestDto careerPackageRequestDto) {
        CareerPackage careerPackage = careerPackageMapper.toCareerPackageFromRequest(careerPackageRequestDto);
        careerPackageRepository.save(careerPackage);
        return careerPackageMapper.toCareerPackageResponseDto(careerPackage);
    }

    public CareerPackageResponseDto getCareerPackageById(UUID id) {
        CareerPackage careerPackage = careerPackageRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Career Package not found")
        );
        return careerPackageMapper.toCareerPackageResponseDto(careerPackage);
    }

    public void deleteCareerPackage(UUID id) {
        careerPackageRepository.deleteById(id);
    }

    public CareerPackageResponseDto updateCareerPackage(UUID id, CareerPackageRequestDto careerPackageRequestDto) {
        CareerPackage careerPackage = careerPackageRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Career Package not found")
        );
        careerPackageMapper.updateCareerPackageFromRequestDto(careerPackageRequestDto, careerPackage);
        careerPackageRepository.save(careerPackage);
        return careerPackageMapper.toCareerPackageResponseDto(careerPackage);
    }

    public Page<CareerPackageResponseDto> getAllCareerPackagesPaginated(Pageable pageable){
        return careerPackageRepository.findAll(pageable).map(careerPackageMapper::toCareerPackageResponseDto);
    }

    public UUID getCareerPackageByTitle(Title title) {
        System.out.println("Reached Service");
        CareerPackage careerPackage = careerPackageRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Career Package not found for title: " + title));
        System.out.println(careerPackage);
        return careerPackage.getId();
    }


}
