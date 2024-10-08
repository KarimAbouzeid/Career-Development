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
@Table(name = "learning_types")
public class LearningTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String typeName;

    private int baseScore;
}
