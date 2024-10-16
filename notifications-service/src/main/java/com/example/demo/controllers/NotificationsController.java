package com.example.demo.controllers;

import com.example.demo.dtos.NotificationDTO;
import com.example.demo.services.NotificationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(
            @PathVariable UUID userId
           ) {

        List<NotificationDTO> notifications = notificationsService.getAllNotifications(userId);

        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID id) {
        notificationsService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNotification(@RequestBody NotificationDTO notificationsDTO) {
        notificationsService.addNotification(notificationsDTO);
        return ResponseEntity.ok("Notification added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable UUID id) {
        NotificationDTO notificationDTO = notificationsService.getNotification(id);
        return new ResponseEntity<>(notificationDTO, HttpStatus.OK);
    }
}
