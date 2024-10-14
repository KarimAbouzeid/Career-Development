package com.example.demo.services;

import com.example.demo.dtos.NotificationsDTO;
import com.example.demo.entities.Notifications;
import com.example.demo.mappers.NotificationMapper;
import com.example.demo.repositories.NotificationsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public List<NotificationsDTO> getAllNotifications(UUID userId) {
        List<Notifications> allNotifications = notificationsRepository.findAll();
        return allNotifications.stream()
                .map(notificationMapper::toNotificationsDTO)
                .collect(Collectors.toList());
    }


    public NotificationsDTO getNotification(UUID notificationId) {
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

    public void addNotification(NotificationsDTO notificationsDTO) {
        Notifications notifications = notificationMapper.toNotifications(notificationsDTO);
        notificationsRepository.save(notifications);
    }
}
