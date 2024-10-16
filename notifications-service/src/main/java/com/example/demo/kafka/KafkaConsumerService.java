package com.example.demo.kafka;

import com.example.demo.dtos.ActionsDTO;
import com.example.demo.dtos.NotificationDataDTO;
import com.example.demo.dtos.NotificationDTO;
import com.example.demo.dtos.ReceivedNotificationDTO;
import com.example.demo.services.ActionsService;
import com.example.demo.services.NotificationDataService;
import com.example.demo.services.NotificationsService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private NotificationDataService notificationDataService;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private ActionsService actionsService;

    @KafkaListener(topics = "notification", groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
    public void createNotification(ReceivedNotificationDTO receivedNotificationDTO) {
        log.info("Received receivedNotificationDTO for adding: {}", receivedNotificationDTO);

        ActionsDTO actionsDTO = new ActionsDTO();
        actionsDTO.setName(receivedNotificationDTO.getActionName());
        ActionsDTO returnedActionsDto = actionsService.addAction(actionsDTO);

        NotificationDataDTO notificationDataDTO = new NotificationDataDTO();
        notificationDataDTO.setDate(receivedNotificationDTO.getDate());
        notificationDataDTO.setEntityType(receivedNotificationDTO.getEntityType());
        notificationDataDTO.setAction_id(returnedActionsDto.getId());
        System.out.println("notificationDataDTO: " + notificationDataDTO);
        System.out.println("actionsDTO: " + returnedActionsDto);
        NotificationDataDTO returnedNotificationDataDTO = notificationDataService.addNotificationData(notificationDataDTO);


        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotification_data_id(returnedNotificationDataDTO.getId());
        notificationDTO.setReceiverId(receivedNotificationDTO.getReceiverId());
        notificationDTO.setSeen(receivedNotificationDTO.isSeen());
        notificationsService.addNotification(notificationDTO);

    }


}
