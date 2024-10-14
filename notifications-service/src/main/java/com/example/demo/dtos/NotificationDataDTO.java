package com.example.demo.dtos;

import com.example.demo.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDataDTO {

    private UUID id;

    private UUID action_id;

    private UUID actorId;

    private Date date;

    private EntityType entityType;
}