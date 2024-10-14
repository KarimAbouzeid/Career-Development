package com.example.demo.dtos;

import com.example.demo.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLearningsDTO {

    private UUID id;

    private UUID userId;

    private UUID learningId;

    private String proof;

    private UUID proofTypeId;

    private UUID activeBoosterId;

    private Date date;

    private ApprovalStatus approvalStatus;

    private String comment;
}
