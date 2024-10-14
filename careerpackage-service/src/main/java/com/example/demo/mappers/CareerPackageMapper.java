package com.example.demo.mappers;


import com.example.demo.dtos.CareerPackageRequestDto;
import com.example.demo.dtos.CareerPackageResponseDto;
import com.example.demo.entities.CareerPackage;
import org.mapstruct.*;


@Mapper(componentModel ="spring")
public interface CareerPackageMapper {
    
    CareerPackageRequestDto toCareerPackageRequestDto(CareerPackage careerPackage);

    @Mapping(target = "id", ignore = true)
    CareerPackage toCareerPackageFromRequest(CareerPackageRequestDto careerPackageRequestDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCareerPackageFromRequestDto(CareerPackageRequestDto careerPackageDto, @MappingTarget CareerPackage careerPackage);


    CareerPackageResponseDto toCareerPackageResponseDto(CareerPackage careerPackage);

    CareerPackage toCareerPackageFromResponse(CareerPackageResponseDto careerPackageResponseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCareerPackageFromResponseDto(CareerPackageResponseDto careerPackageResponseDto, @MappingTarget CareerPackage careerPackage);


}



