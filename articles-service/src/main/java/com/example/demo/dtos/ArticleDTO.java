package com.example.demo.dtos;

import com.example.demo.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDTO {
    private UUID id;

    private String title;

    private UUID author;

    private Date submissionDate;

    private ApprovalStatus approvalStatus;

    private String comment;

    private String document;

}