package com.example.demo.services;

import com.example.demo.dtos.NotificationDTO;
import com.example.demo.entities.Notifications;
import com.example.demo.mappers.NotificationMapper;
import com.example.demo.repositories.NotificationsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationsService {
    private final NotificationsRepository notificationsRepository;
    private final NotificationMapper notificationMapper;

    public NotificationsService(NotificationsRepository notificationsRepository, NotificationMapper notificationMapper) {
        this.notificationsRepository = notificationsRepository;
        this.notificationMapper = notificationMapper;
    }

    public List<NotificationDTO> getAllNotifications(UUID userId) {
        List<Notifications> allNotifications = notificationsRepository.findAll();
        return allNotifications.stream()
                .map(notificationMapper::toNotificationsDTO)
                .collect(Collectors.toList());
    }


    public NotificationDTO getNotification(UUID notificationId) {
        Notifications notifications = notificationsRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification with id " + notificationId + " not found"));

        return notificationMapper.toNotificationsDTO(notifications);
    }

    public void deleteNotification(UUID id) {
        if (notificationsRepository.existsById(id)) {
            notificationsRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Notification with id " + id + " does not exist.");
        }
    }

    public void addNotification(NotificationDTO notificationsDTO) {
        Notifications notifications = notificationMapper.toNotifications(notificationsDTO);
        notificationsRepository.save(notifications);
    }
}
