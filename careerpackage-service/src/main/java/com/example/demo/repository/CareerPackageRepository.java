package com.example.demo.repository;

import com.example.demo.entities.CareerPackage;
import com.example.demo.enums.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CareerPackageRepository extends JpaRepository<CareerPackage, UUID> {

    Optional<CareerPackage> findByTitle(Title title);
}
