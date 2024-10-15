package com.example.demo.dtos;

import com.example.demo.enums.EntityType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDTO {

    private String actionName;

    private Date date;

    private EntityType entityType;

    private UUID receiverId;

    private boolean seen;

}



