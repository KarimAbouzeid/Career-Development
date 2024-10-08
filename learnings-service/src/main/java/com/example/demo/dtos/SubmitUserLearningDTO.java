package com.example.demo.dtos;

import com.example.demo.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitUserLearningDTO {
    private UUID userId;
    private UUID learningId; // Optional for existing learning
    private String proof;
    private UUID proofTypeId;
    private UUID activeBoosterId; // Optional

    private UUID learningTypeId; // Optional if existing learning
    private String URL;
    private String description;
    private UUID learningSubjectId;
    private float lengthInHours;

    private Date date;
    private ApprovalStatus approvalStatus;
    private String comment;
}
