package com.parcellocker.parcelhandlerservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcellocker.parcelhandlerservice.payload.ParcelSendingNotification;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelPickingUpNotification;
import com.parcellocker.parcelhandlerservice.payload.kafka.ParcelShippingNotification;
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
    private NewTopic topic3;

    @Autowired
    private NewTopic topic4;

    @Autowired
    private NewTopic topic5;

    @Autowired
    private NewTopic topic6;

    @Autowired
    private KafkaTemplate<String, ParcelSendingNotification> kafkaTemplate1;

    @Autowired
    private KafkaTemplate<String, ParcelSendingNotification> kafkaTemplate2;

    @Autowired
    private KafkaTemplate<String, ParcelShippingNotification> kafkaTemplate3;

    @Autowired
    private KafkaTemplate<String, ParcelShippingNotification> kafkaTemplate4;

    @Autowired
    private KafkaTemplate<String, ParcelPickingUpNotification> kafkaTemplate5;

    @Autowired
    private KafkaTemplate<String, ParcelPickingUpNotification> kafkaTemplate6;

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

    //Szállítás utáni email értesítés a csomag feladójának
    public void sendShippingNotificationForSender(ParcelShippingNotification notification){

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
                .setHeader(KafkaHeaders.TOPIC , topic3.name())
                .build();
        kafkaTemplate3.send(message);

    }

    //Szállítás utáni email értesítés a csomag átvevőjének
    public void sendShippingNotificationForReceiver(ParcelShippingNotification notification){

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
                .setHeader(KafkaHeaders.TOPIC , topic4.name())
                .build();
        kafkaTemplate4.send(message);

    }

    //Átvétel utáni email értesítés a csomag átvevőjének
    public void sendPickingUpNotificationForReceiver(ParcelPickingUpNotification notification){

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
                .setHeader(KafkaHeaders.TOPIC , topic5.name())
                .build();
        kafkaTemplate5.send(message);

    }

    //Átvétel utáni email értesítés a csomag feladójának
    public void sendPickingUpNotificationForSender(ParcelPickingUpNotification notification){

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
                .setHeader(KafkaHeaders.TOPIC , topic6.name())
                .build();
        kafkaTemplate6.send(message);

    }
}

