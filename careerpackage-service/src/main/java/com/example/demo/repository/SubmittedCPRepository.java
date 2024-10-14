package com.example.demo.repository;

import com.example.demo.entities.CareerPackage;
import com.example.demo.entities.SubmittedCP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubmittedCPRepository extends JpaRepository<SubmittedCP, UUID> {


}
