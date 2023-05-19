package com.parcellocker.authenticationservice.kafka;

import com.parcellocker.authenticationservice.payload.response.SignUpActivationDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

//Adat küldése a(z) (signup-email-topic) topic-nak
@Service
@AllArgsConstructor
@NoArgsConstructor
public class Producer {

    @Autowired
    private NewTopic topic;

    @Autowired
    private KafkaTemplate<String, SignUpActivationDTO> kafkaTemplate;

    //Regisztrációhoz szükséges aktiváló kód küldése a "signup_email_topic" nevű topic-nak
    public void sendActivationCodeForSignUp(SignUpActivationDTO signUpActivationDTO){

        Message<SignUpActivationDTO> message = MessageBuilder
                .withPayload(signUpActivationDTO)
                .setHeader(KafkaHeaders.TOPIC , topic.name())
                .build();
        kafkaTemplate.send(message);

    }
}
