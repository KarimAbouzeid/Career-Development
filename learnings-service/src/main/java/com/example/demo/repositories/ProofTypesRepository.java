package com.example.demo.repositories;

import com.example.demo.entities.ProofTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProofTypesRepository extends JpaRepository<ProofTypes, UUID> {
}
