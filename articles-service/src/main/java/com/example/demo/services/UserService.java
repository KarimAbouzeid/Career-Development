package com.example.demo.services;

import com.example.demo.dtos.ReceivedNotificationDTO;
import com.example.demo.enums.EntityType;
import com.example.demo.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8080/api/users";

    public UUID getManager(UUID id) {
        String url = USER_SERVICE_URL + "/getManager/" + id;

        ResponseEntity<UUID> response = restTemplate.getForEntity(url, UUID.class);

        return response.getBody();
    }


}
