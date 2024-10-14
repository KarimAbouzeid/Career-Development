package com.example.demo.controllers;


import com.example.demo.dtos.CareerPackageRequestDto;
import com.example.demo.dtos.CareerPackageResponseDto;
import com.example.demo.enums.Title;
import com.example.demo.service.CareerPackageService;
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
@RequestMapping("/api/careerPackages")
public class CareerPackageController {

    private final CareerPackageService careerPackageService;

    @Autowired
    public CareerPackageController(CareerPackageService careerPackageService) {
        this.careerPackageService = careerPackageService;

    }

    @PostMapping("/add")
    public ResponseEntity<CareerPackageResponseDto> addCareerPackage(@RequestBody CareerPackageRequestDto careerPackageDto) {
        CareerPackageResponseDto createdCareerPackage = careerPackageService.addCareerPackage(careerPackageDto);
        return new ResponseEntity<>(createdCareerPackage, HttpStatus.CREATED);
    }

    //Called By Backend only
    @GetMapping("/getCareerPackageByTitle/{title}")
    public ResponseEntity<UUID> getCareerPackageByTitle(@PathVariable  Title title) {
        System.out.println("Reached Controller");
        UUID careerPackageId = careerPackageService.getCareerPackageByTitle(title);
        return ResponseEntity.ok(careerPackageId);
    }



    @GetMapping("/getAllCareerPackagesPaginated")
    public ResponseEntity<Page<CareerPackageResponseDto>> getAllCareerPackages(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CareerPackageResponseDto> careerPackages = careerPackageService.getAllCareerPackagesPaginated(pageable);
        return ResponseEntity.ok(careerPackages);
    }







}
