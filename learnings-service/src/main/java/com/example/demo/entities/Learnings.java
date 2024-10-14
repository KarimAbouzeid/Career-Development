package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "learnings")
public class Learnings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String Title;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private LearningTypes learningType;

    private String URL;

    private String description;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private LearningSubjects learningSubject;

    private float lengthInHours;
}
