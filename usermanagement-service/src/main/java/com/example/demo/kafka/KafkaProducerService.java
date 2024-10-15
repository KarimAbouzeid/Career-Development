package com.example.demo.kafka;

import com.example.demo.dtos.UserScoresDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void addUserScore(String topic, UserScoresDTO payload) {
        log.info("Sent UserScoresDTO for adding: {}", payload);

        kafkaTemplate.send(topic, payload);
    }

    public void deleteUserScore(String topic, UUID userId) {
        log.info("Sent UUID for deleting user score: {}", userId);
        kafkaTemplate.send(topic, userId);
    }


}
