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
    //Csomag feladásakor
    private String topic1 = "parcelSendingNotificationForSender";
    //Kafka topic neve. Csomag feladási email értesítés küldése a csomag átvevőjének.
    //Csomag feladásakor
    private String topic2 = "parcelSendingNotificationForReceiver";

    //Kafka topic neve. Csomag feladási email értesítés küldése a csomag feladójának.
    //Csomag leszállításakor
    private String topic3 = "parcelShippingNotificationForSender";
    //Kafka topic neve. Csomag feladási email értesítés küldése a csomag átvevőjének.
    //Csomag leszállításakor
    private String topic4 = "parcelShippingNotificationForReceiver";

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

    //Topic objektum létrehozása
    @Bean
    public NewTopic topic3(){
        return TopicBuilder.name(topic3)
                .build();
    }

    //Topic objektum létrehozása
    @Bean
    public NewTopic topic4(){
        return TopicBuilder.name(topic4)
                .build();
    }

}
