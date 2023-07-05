package com.parcellocker.parcelhandlerservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//Topic létrehozása
@Configuration
public class KafkaTopicConfig {

    //Kafka topic neve. Csomag feladási email értesítés küldése a csomag feladójának.
    private String topic1 = "parcelSendingNotificationForSender";
    //Kafka topic neve. Csomag feladási email értesítés küldése a csomag átvevőjének.
    private String topic2 = "parcelSendingNotificationForReceiver";

    //Topic objektum létrehozása
    @Bean
    public NewTopic topic1(){
        return TopicBuilder.name(topic1)
                .build();
    }

    //Topic objektum létrehozása
    @Bean
    public NewTopic topic2(){
        return TopicBuilder.name(topic2)
                .build();
    }
}
