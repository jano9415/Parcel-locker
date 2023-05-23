package com.parcellocker.notificationservice.service;

import com.parcellocker.authenticationservice.payload.response.SignUpActivationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    //Regisztrációs kód küldése email-ben
    public void sendActivationCodeForSignUp(SignUpActivationDTO signUpActivationDTO) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(signUpActivationDTO.getEmailAddress());
            message.setSubject("Regisztráció aktiválása");
            message.setText("Kedves " + signUpActivationDTO.getLastName() + " " + signUpActivationDTO.getFirstName() + "\n\n"
                    + "Köszönjük hogy regisztrált a Swiftpost csomagküldő rendszerben.\n"
                    + "A regisztráció véglegesítéséhez kattintson az alábbi linkre.\n"
                    + "http://192.168.0.13:3000/login/" + signUpActivationDTO.getActivationCode()
                    + "");
            javaMailSender.send(message);

        }
        catch (Exception e){

        }


    }
}
