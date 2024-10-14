//package com.example.demo.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class KafkaListenerExample {
//
//    @KafkaListener(topics = "topic-1", groupId = "group1")
//    void listener(String data) {
//        log.info("Received message [{}] in group1", data);
//    }
//
//    @KafkaListener(topics = "topic-1,topic-2", groupId = "group1")
//    void listener(@Payload String data,
//                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
//                  @Header(KafkaHeaders.OFFSET) int offset) {
//        log.info("Received message [{}] from group1, partition-{} with offset-{}",
//                data,
//                partition,
//                offset);
//    }
//}
