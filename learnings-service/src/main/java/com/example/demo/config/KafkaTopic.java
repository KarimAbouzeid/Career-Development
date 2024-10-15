package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic userScoresTopic() {
        return new NewTopic("userscores-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic userIdTopic() {
        return new NewTopic("userId-delete", 1, (short) 1);
    }

    @Bean
    public NewTopic notificationTopic() {
        return new NewTopic("notification", 1, (short) 1);
    }
}
