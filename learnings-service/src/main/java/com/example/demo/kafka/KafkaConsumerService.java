package com.example.demo.kafka;

import com.example.demo.dtos.UserScoresDTO;
import com.example.demo.services.UserScoresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private UserScoresService userScoresService;

    @KafkaListener(topics = "${kafka.topic.userScores}", groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
    public void addUserScore(UserScoresDTO userScoresDTO) {
        userScoresService.addUserScore(userScoresDTO);
        log.info("Received UserScoresDTO for adding: {}", userScoresDTO);

    }
    @KafkaListener(topics = "${kafka.topic.userId}", groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
    public void deleteUserScore(UUID userId) {
        userScoresService.deleteUserScore(userId);
        log.info("Received userId for deletion: {}", userId);
    }
}
