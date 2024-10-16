package com.example.demo.service;

import com.example.demo.dtos.ReceivedNotificationDTO;
import com.example.demo.enums.EntityType;
import com.example.demo.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private final KafkaProducerService kafkaProducerService;


    private static final String USER_SERVICE_URL = "http://localhost:8080/api/users";

    @Value("notification")
    private String approvalNotificationTopic;

    public UserService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public UUID getManager(UUID id) {
        String url = USER_SERVICE_URL + "/getManager/" + id;

        ResponseEntity<UUID> response = restTemplate.getForEntity(url, UUID.class);

        return response.getBody();
    }

    public List<UUID> fetchAllUserIds() {
        String url = USER_SERVICE_URL + "/allUsersIds";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return response.getBody();
    }

    public void sendNotificationsToAll(String message) {

        List<UUID> userIds = fetchAllUserIds();
        ReceivedNotificationDTO notification = new ReceivedNotificationDTO(
                message,
                new Date(),
                EntityType.Admin,
                userIds
        );

        kafkaProducerService.sendNotification(approvalNotificationTopic, notification);


    }



}
