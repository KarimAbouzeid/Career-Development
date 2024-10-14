package com.example.demo.entities;

import com.example.demo.enums.EntityType;
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
@Table(name = "notification_data")
public class NotificationData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = true)
    private Actions action;

    private UUID actorId;

    private Date date;

    private EntityType entityType;
}