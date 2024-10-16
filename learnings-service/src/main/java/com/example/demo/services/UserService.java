package com.example.demo.services;

import com.example.demo.dtos.ReceivedNotificationDTO;
import com.example.demo.enums.EntityType;
import com.example.demo.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<String> fetchAllUserIds() {
        String url = USER_SERVICE_URL + "/allUsersIds";
        System.out.println("Fetching all user ids " + url);

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        System.out.println("CLASS: " + response.getBody().getClass());
        return response.getBody();
    }

    public void sendNotificationsToAll(String message) {
    try{
        System.out.println("Sending notification to all users");
        List<String> userIds = fetchAllUserIds();


        List<UUID> uuidList = userIds.stream()
                .map(UUID::fromString) // Convert each String to UUID
                .toList(); // Collect the results into a List<UUID>


        ReceivedNotificationDTO notification = new ReceivedNotificationDTO(
                message,
                new Date(),
                EntityType.Admin,
                uuidList

        );

        System.out.println("Sending notificationssss to all users");
        kafkaProducerService.sendNotification(approvalNotificationTopic, notification);
    }catch (Exception e){
        System.out.println("Error in sending notification to all users");
        System.out.println(e.getMessage());
        System.out.println(e.getCause());
    }


    }

}
