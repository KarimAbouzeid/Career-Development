package com.example.demo.entities;

import com.example.demo.enums.Title;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "career_package_document")
public class CareerPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(name = "google_doc_link", nullable = false)
    private String googleDocLink;

    @OneToMany(mappedBy = "careerPackage")
    private Set<SubmittedCP> submittedCareerPackages;

}
