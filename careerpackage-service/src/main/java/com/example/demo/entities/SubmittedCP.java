package com.example.demo.entities;

import com.example.demo.enums.Status;
import com.example.demo.enums.Title;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "submitted_career_package")

public class SubmittedCP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID submissionId;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "careerPackageId", nullable = false)
    private CareerPackage careerPackage;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "manager_id", nullable = false)
    private UUID managerId;

    @Enumerated(EnumType.STRING)
    private Title title;

    private String googleDocLink;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;





}
