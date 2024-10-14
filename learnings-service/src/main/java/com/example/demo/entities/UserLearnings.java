package com.example.demo.entities;

import com.example.demo.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_learnings")
public class UserLearnings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "learning_id", nullable = false)
    private Learnings learning;

    private String proof;

    @ManyToOne
    @JoinColumn(name = "proof_type_id", nullable = false)
    private ProofTypes proofType;

    @ManyToOne
    @JoinColumn(name = "activebooster_id", nullable = true)
    private Boosters activeBooster;

    private Date date;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private String comment;
}
