package com.parcellocker.notificationservice.kafka;

import com.parcellocker.authenticationservice.payload.response.SignUpActivationDTO;
import com.parcellocker.notificationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



//Kafka topic olvasása
@Service
public class Consumer {

    @Autowired
    private EmailService emailService;

    //Feliratkozok a topic-ra. A topic neve és group-id az application.properties-ből jön
    //Group-id: notification-service
    //Topic név: signup_email_topic
    //Regisztrációhoz szükséges aktivációs kód olvasása
    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void readActivationCodeForSignUp(SignUpActivationDTO signUpActivationDTO){
        emailService.sendActivationCodeForSignUp(signUpActivationDTO);


    }
}