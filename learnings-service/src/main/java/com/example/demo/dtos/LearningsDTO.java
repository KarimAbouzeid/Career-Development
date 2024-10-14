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
    private String title;
    private String URL;
    private String description;
    private float lengthInHours;
    private UUID learningTypeId; // assuming you want to include the ID of the learning type
    private UUID learningSubjectId; // assuming you want to include the ID of the learning subject
}
