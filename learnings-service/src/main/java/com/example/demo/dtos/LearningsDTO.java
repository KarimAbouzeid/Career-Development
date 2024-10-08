package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningsDTO {
    private UUID id;
    private UUID learningTypeId;
    private String URL;
    private String description;
    private UUID learningSubjectId;
    private float lengthInHours;
}
