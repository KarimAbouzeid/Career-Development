package com.example.demo.dtos;

import com.example.demo.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLearningResponseDTO {
    private UUID id;
    private String Title;
    private String URL;
    private String proof;
    private String proofTypeName;
    private Date date;
    private ApprovalStatus approvalStatus;
    private String comment;
    private float lengthInHours;
    private int baseScore;
}
