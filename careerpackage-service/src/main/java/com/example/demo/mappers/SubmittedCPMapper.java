package com.example.demo.mappers;


import com.example.demo.dtos.SubmittedCPDto;
import com.example.demo.entities.SubmittedCP;
import org.mapstruct.*;

@Mapper(componentModel ="spring")
public interface SubmittedCPMapper {

    @Mapping(source = "careerPackage.id", target = "careerPackageId")
    SubmittedCPDto toSubmittedCPDto(SubmittedCP submittedCP);

    @Mapping(target = "careerPackage.id", source = "careerPackageId")
    SubmittedCP toSubmittedCP(SubmittedCPDto submittedCareerPackageDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubmittedCareerPackageFromDto(SubmittedCPDto submittedCPDto, @MappingTarget SubmittedCP submittedCP);




}
