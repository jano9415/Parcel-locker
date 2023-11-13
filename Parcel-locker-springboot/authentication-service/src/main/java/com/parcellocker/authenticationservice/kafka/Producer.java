package com.parcellocker.authenticationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcellocker.authenticationservice.payload.kafka.SecondFactorDTO;
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
    private NewTopic topic2;

    //String -> Object, Object -> String
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, SignUpActivationDTO> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, SecondFactorDTO> kafkaTemplate2;

    //Regisztrációhoz szükséges aktiváló kód küldése a "signup_email_topic" nevű topic-nak
    public void sendActivationCodeForSignUp(SignUpActivationDTO signUpActivationDTO){

        //Objektum konvertálása string-be
        String jsonString;

        try {
            jsonString = objectMapper.writeValueAsString(signUpActivationDTO);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Message<String> message = MessageBuilder
                .withPayload(jsonString)
                .setHeader(KafkaHeaders.TOPIC , topic.name())
                .build();
        kafkaTemplate.send(message);

    }

    //Második faktor kód küldése a "secondFactor" nevű topic-nak
    public void sendSecondFactorCode(SecondFactorDTO secondFactorDTO){

        //Objektum konvertálása string-be
        String jsonString;

        try {
            jsonString = objectMapper.writeValueAsString(secondFactorDTO);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Message<String> message = MessageBuilder
                .withPayload(jsonString)
                .setHeader(KafkaHeaders.TOPIC , topic2.name())
                .build();
        kafkaTemplate2.send(message);

    }
}
