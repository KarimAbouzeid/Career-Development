package com.example.demo.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDTO {

    private UUID id;

    private UUID notification_data_id;

    private UUID receiverId;

    private boolean seen;
}