package com.parcellocker.notificationservice.kafka;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcellocker.notificationservice.payload.ParcelSendingNotification;
import com.parcellocker.notificationservice.payload.SignUpActivationDTO;
import com.parcellocker.notificationservice.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



//Kafka topic olvasása
@Service
public class Consumer {

    @Autowired
    private EmailService emailService;

    //String -> Object, Object -> String
    ObjectMapper objectMapper = new ObjectMapper();

    //Feliratkozok a topic-ra. A topic neve és group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: signup_email_topic
    //Regisztrációhoz szükséges aktivációs kód olvasása
    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void readActivationCodeForSignUp(String signUpActivationDTO){

        //String konvertálása obejektumba
        SignUpActivationDTO jsonObject;

        try {
            jsonObject = objectMapper.readValue(signUpActivationDTO, SignUpActivationDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendActivationCodeForSignUp(jsonObject);
    }


    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelSendingNotificationForSender
    //Csomagfeladás utáni email értesítés küldése a feladónak
    @KafkaListener(topics = "parcelSendingNotificationForSender" , groupId = "${spring.kafka.consumer.group-id}")
    public void sendNotificationForSender(String notification){

        //String konvertálása obejektumba
        ParcelSendingNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelSendingNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendNotificationForSender(jsonObject);
    }

    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelSendingNotificationForReceiver
    //Csomagfeladás utáni email értesítés küldése az átvevőnek
    @KafkaListener(topics = "parcelSendingNotificationForReceiver", groupId = "${spring.kafka.consumer.group-id}")
    public void sendNotificationForReceiver(String notification){

        //String konvertálása objektumba
        ParcelSendingNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelSendingNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendNotificationForReceiver(jsonObject);
    }


}
