package com.parcellocker.parcelhandlerservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingNotification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

//Adat küldése a(z) (parcelSendingNotificationForSender) topic-nak

@Service
@AllArgsConstructor
@NoArgsConstructor
public class Producer {



    @Autowired
    private NewTopic topic1;

    @Autowired
    private NewTopic topic2;

    @Autowired
    private KafkaTemplate<String, ParcelSendingNotification> kafkaTemplate1;

    @Autowired
    private KafkaTemplate<String, ParcelSendingNotification> kafkaTemplate2;

    //String -> Object, Object -> String
    ObjectMapper objectMapper = new ObjectMapper();

    //Csomagfeladás utáni email értesítés a csomag feladójának
    public void sendNotificationForSender(ParcelSendingNotification notification){

        //Objektum konvertálása string-be
        String jsonString;

        try {
            jsonString = objectMapper.writeValueAsString(notification);
        }
         catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //Üzenet küldése a topic-nak
        Message<String> message = MessageBuilder
                .withPayload(jsonString)
                .setHeader(KafkaHeaders.TOPIC , topic1.name())
                .build();
        kafkaTemplate1.send(message);

    }

    //Csomagfeladás utáni email értesítés a csomag átvevőjének
    public void sendNotificationForReceiver(ParcelSendingNotification notification){

        //Objektum konvertálása string-be
        String jsonString;

        try {
            jsonString = objectMapper.writeValueAsString(notification);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //Üzenet küldése a topic-nak

        Message<String> message = MessageBuilder
                .withPayload(jsonString)
                .setHeader(KafkaHeaders.TOPIC , topic2.name())
                .build();
        kafkaTemplate2.send(message);

    }
}

