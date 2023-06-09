package com.parcellocker.authenticationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//Topic létrehozása
@Configuration
public class KafkaTopicConfig {

    //Topic neve. Az adat az application.properties-ből jön
    //A topic neve: signup_email_topic
    @Value("${spring.kafka.topic.name}")
    private String topicName;

    //Topic objektum létrehozása
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topicName)
                .build();
    }
}
