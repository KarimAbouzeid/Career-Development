package com.example.demo.kafka;

import com.example.demo.messages.UserSubmissionMSG;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaSenderExample {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCustomMessage(String example) {
        log.info("Sending Json Serializer : {}", example);
        log.info("--------------------------------");

        kafkaTemplate.send("topic1", example);
    }

}
