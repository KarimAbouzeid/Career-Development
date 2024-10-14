package com.example.demo.controllers;

import com.example.demo.dtos.NotificationDataDTO;
import com.example.demo.services.NotificationDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/notificationsData")
public class NotificationsDataController {

    private final NotificationDataService notificationDataService;

    public NotificationsDataController(NotificationDataService notificationDataService) {
        this.notificationDataService = notificationDataService;
    }


    @GetMapping("/all/{userId}")
    public ResponseEntity<List<NotificationDataDTO>> getAllNotificationsData(
            @PathVariable UUID userId
           ) {

        List<NotificationDataDTO> notificationsDataDTO = notificationDataService.getAllNotificationData(userId);

        return ResponseEntity.ok(notificationsDataDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID id) {
        notificationDataService.deleteNotificationData(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNotification(@RequestBody NotificationDataDTO notificationsDataDTO) {
        notificationDataService.addNotificationData(notificationsDataDTO);
        return ResponseEntity.ok("Notification data added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDataDTO> getNotification(@PathVariable UUID id) {
        NotificationDataDTO notificationDataDTO = notificationDataService.getNotificationData(id);
        return new ResponseEntity<>(notificationDataDTO, HttpStatus.OK);
    }
}
