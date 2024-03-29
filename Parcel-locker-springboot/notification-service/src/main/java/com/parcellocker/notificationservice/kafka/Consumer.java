package com.parcellocker.notificationservice.kafka;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcellocker.notificationservice.payload.ParcelSendingNotification;
import com.parcellocker.notificationservice.payload.SignUpActivationDTO;
import com.parcellocker.notificationservice.payload.kafka.*;
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

    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelShippingNotificationForSender
    //Szállítás utáni email értesítés küldése a feladónak
    @KafkaListener(topics = "parcelShippingNotificationForSender" , groupId = "${spring.kafka.consumer.group-id}")
    public void sendShippingNotificationForSender(String notification){

        //String konvertálása obejektumba
        ParcelShippingNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelShippingNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendShippingNotificationForSender(jsonObject);
    }

    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelShippingNotificationForReceiver
    //Szállítás utáni email értesítés küldése az átvevőnek
    @KafkaListener(topics = "parcelShippingNotificationForReceiver", groupId = "${spring.kafka.consumer.group-id}")
    public void sendShippingNotificationForReceiver(String notification){

        //String konvertálása objektumba
        ParcelShippingNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelShippingNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendShippingNotificationForReceiver(jsonObject);
    }

    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelPickingUpNotificationForReceiver
    //Átvétel utáni email értesítés küldése az átvevőnek
    @KafkaListener(topics = "parcelPickingUpNotificationForReceiver", groupId = "${spring.kafka.consumer.group-id}")
    public void parcelPickingUpNotificationForReceiver(String notification){

        //String konvertálása objektumba
        ParcelPickingUpNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelPickingUpNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendPickingUpNotificationForReceiver(jsonObject);
    }

    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelPickingUpNotificationForSender
    //Átvétel utáni email értesítés küldése a feladónak
    @KafkaListener(topics = "parcelPickingUpNotificationForSender", groupId = "${spring.kafka.consumer.group-id}")
    public void sendPickingUpNotificationForSender(String notification){

        //String konvertálása objektumba
        ParcelPickingUpNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelPickingUpNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendPickingUpNotificationForSender(jsonObject);
    }

    //Feliratkozok a topic-ra. A group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: parcelSendingFromWebPageNotification
    //Csomagfeladás a weboldalról értesítés
    @KafkaListener(topics = "parcelSendingFromWebPageNotification", groupId = "${spring.kafka.consumer.group-id}")
    public void parcelSendingFromWebPageNotification(String notification){

        //String konvertálása objektumba
        ParcelSendingFromWebPageNotification jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ParcelSendingFromWebPageNotification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.parcelSendingFromWebPageNotification(jsonObject);
    }

    //Második faktor kód küldése a bejelentkezéshez email-ben
    //secondFactor nevű topic
    @KafkaListener(topics = "secondFactor", groupId = "${spring.kafka.consumer.group-id}")
    public void sendSecondFactorCode(String notification){

        //String konvertálása objektumba
        SecondFactorDTO jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, SecondFactorDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.sendSecondFactorCode(jsonObject);
    }

    //Új jelszó küldése email-ben
    //forgotPassword nevű topic
    @KafkaListener(topics = "forgotPassword", groupId = "${spring.kafka.consumer.group-id}")
    public void forgotPassword(String notification){

        //String konvertálása objektumba
        ForgotPasswordDTO jsonObject;

        try {
            jsonObject = objectMapper.readValue(notification, ForgotPasswordDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        emailService.forgotPassword(jsonObject);
    }


}
