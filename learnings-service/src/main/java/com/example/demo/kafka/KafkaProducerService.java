package com.example.demo.kafka;

import com.example.demo.dtos.ReceivedNotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotification(String topic, ReceivedNotificationDTO payload) {

        System.out.println("PAYLOAD: " + payload);
        System.out.println("TOPIC: " + topic);
        kafkaTemplate.send(topic, payload);
        System.out.println("SENT");
    }




}
