package com.example.demo;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    @GetMapping("/allNotifications/{userId}")
    public ResponseEntity<List<NotificationsDTO>> getAllNotifications(@PathVariable UUID userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NotificationsDTO> notifications = notificationsService.getAllNotifications(userId, pageable);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable UUID id) {
        notificationsService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @PostMapping("/addNotification")
    public ResponseEntity<> addNotification(@RequestBody NotificationsDTO notificationsDTO) {
        NotificationsDTO newNotification = notificationsService.addNotification(notificationsDTO);
        return ResponseEntity.ok(newNotification);
    }



}
