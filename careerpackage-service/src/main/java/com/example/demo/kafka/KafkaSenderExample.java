//package com.example.demo.kafka;
//
//import com.example.demo.messages.UserSubmissionMSG;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Slf4j
//public class KafkaSenderExample {
//
//    @Autowired
//    private KafkaTemplate<String, UserSubmissionMSG> userSubmissionKafkaTemplate;
//
//    public void sendCustomMessage(UserSubmissionMSG userSubmission, String topicName) {
//        log.info("Sending Json Serializer : {}", userSubmission);
//        log.info("--------------------------------");
//
//        userSubmissionKafkaTemplate.send(topicName, userSubmission);
//    }
//
//}
