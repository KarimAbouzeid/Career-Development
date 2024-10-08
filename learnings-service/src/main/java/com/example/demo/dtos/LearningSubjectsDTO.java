package com.example.demo.dtos;

import com.example.demo.enums.SubjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningSubjectsDTO {
    private UUID id;
    private SubjectType type;
    private String subject;
}
