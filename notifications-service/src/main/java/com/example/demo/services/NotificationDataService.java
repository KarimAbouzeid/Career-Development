package com.example.demo.services;

import com.example.demo.dtos.NotificationDataDTO;
import com.example.demo.entities.NotificationData;
import com.example.demo.mappers.NotificationDataMapper;
import com.example.demo.repositories.NotificationsDataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationDataService {
    private final NotificationsDataRepository notificationsDataRepository;
    private final NotificationDataMapper notificationDataMapper;

    public NotificationDataService(NotificationsDataRepository notificationsDataRepository, NotificationDataMapper notificationDataMapper) {
        this.notificationsDataRepository = notificationsDataRepository;
        this.notificationDataMapper = notificationDataMapper;
    }


    public List<NotificationDataDTO> getAllNotificationData(UUID userId) {
        List<NotificationData> allNotificationData = notificationsDataRepository.findAll();
        return allNotificationData.stream()
                .map(notificationDataMapper::toNotificationDataDTO)
                .collect(Collectors.toList());
    }


    public NotificationDataDTO getNotificationData(UUID notificationId) {
        NotificationData NotificationData = notificationsDataRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification data with id " + notificationId + " not found"));

        return notificationDataMapper.toNotificationDataDTO(NotificationData);
    }

    public void deleteNotificationData(UUID id) {
        if (notificationsDataRepository.existsById(id)) {
            notificationsDataRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Notification data with id " + id + " does not exist.");
        }
    }

    public void addNotificationData(NotificationDataDTO NotificationDataDTO) {
        NotificationData NotificationData = notificationDataMapper.toNotificationData(NotificationDataDTO);
        notificationsDataRepository.save(NotificationData);
    }
}
