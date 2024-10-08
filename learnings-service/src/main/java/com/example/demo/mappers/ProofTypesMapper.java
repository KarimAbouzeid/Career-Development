package com.example.demo.mappers;

import com.example.demo.dtos.ProofTypesDTO;
import com.example.demo.entities.ProofTypes;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProofTypesMapper {

    ProofTypesDTO toProofTypesDTO(ProofTypes proofTypes);

    ProofTypes toProofTypes(ProofTypesDTO proofTypesDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProofTypesFromDTO(ProofTypesDTO proofTypesDTO, @MappingTarget ProofTypes entity);
}
