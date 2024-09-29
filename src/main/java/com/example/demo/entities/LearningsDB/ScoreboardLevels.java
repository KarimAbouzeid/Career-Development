package com.example.demo.entities.LearningsDB;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="scoreboardLevels")
public class ScoreboardLevels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String levelName;

    private int minScore;

}
