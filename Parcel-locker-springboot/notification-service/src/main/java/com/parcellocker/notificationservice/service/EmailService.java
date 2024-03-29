package com.parcellocker.notificationservice.service;



import com.parcellocker.notificationservice.payload.ParcelSendingNotification;
import com.parcellocker.notificationservice.payload.SignUpActivationDTO;
import com.parcellocker.notificationservice.payload.kafka.*;
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
            System.out.println("Email server error.");
        }
    }


    //Csomagfeladás utáni email értesítés küldése a feladónak
    public void sendNotificationForSender(ParcelSendingNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getSenderEmailAddress());
            message.setSubject("Csomagfeladás");
            message.setText("Kedves " + notification.getSenderName() + "\n\n"
                    + "Ön " + notification.getSendingDate() + " " + notification.getSendingTime() + "-kor " +
                    "sikeresen feladta a(z) " + notification.getUniqueParcelId() + " azonosítójú csomagját.\n"
                    + "A csomag ára: " + notification.getPrice() + " Ft.\n"
                    + "A csomag címzettje: " + notification.getReceiverName() + "\n"
                    + "A feladás helye: " + notification.getSenderParcelLockerPostCode() + " " + notification.getSenderParcelLockerCity()
                    + " " + notification.getSenderParcelLockerStreet() + "\n"
                    + "Az érkezés helye: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n"
                    + "Ha a feladott csomagja megérkezik a kiválasztott automatába, újabb értesítést fogunk küldeni.");
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }

    //Csomagfeladás utáni email értesítés küldése az átvevőnek
    public void sendNotificationForReceiver(ParcelSendingNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getReceiverEmailAddress());
            message.setSubject("Csomagfeladás");
            message.setText("Kedves " + notification.getReceiverName() + "\n\n"
                    + "Önnek " + notification.getSendingDate() + " " + notification.getSendingTime() + "-kor " +
                    notification.getUniqueParcelId() + " csomagazonosítóval csomagot adtak fel.\n"
                    + "A csomag ára: " + notification.getPrice() + " Ft.\n"
                    + "A csomag feladója: " + notification.getSenderName() + "\n"
                    + "A feladás helye: " + notification.getSenderParcelLockerPostCode() + " " + notification.getSenderParcelLockerCity()
                    + " " + notification.getSenderParcelLockerStreet() + "\n"
                    + "Az érkezés helye: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n"
                    + "Ha a feladott csomag megérkezik a kiválasztott automatába, újabb értesítést fogunk küldeni," +
                    " amiben megtalálja az átvételhez szükséges nyitókódot.");
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }

    //Szállítás utáni email küldése a csomag feladójának
    public void sendShippingNotificationForSender(ParcelShippingNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getSenderEmailAddress());
            message.setSubject("Feladott csomagja megérkezett");
            message.setText("Kedves " + notification.getSenderName() + "\n\n"
                    + "Az Ön " + notification.getUniqueParcelId() + " azonosítójú csomagja " + notification.getShippingDate()
                    + " " + notification.getShippingTime() + "-kor megérkezett a feladott automatába." + "\n"
                    + "A csomag ára: " + notification.getPrice() + " Ft.\n"
                    + "A csomag címzettje: " + notification.getReceiverName() + "\n"
                    + "A feladás helye: " + notification.getSenderParcelLockerPostCode() + " " + notification.getSenderParcelLockerCity()
                    + " " + notification.getSenderParcelLockerStreet() + "\n"
                    + "Az érkezés helye: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n"
                    + "Ha a feladott csomagot átveszik, újabb email értesítést fogunk küldeni." + "\n"

            );
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }

    }

    //Szállítás utáni email küldése a csomag átvevőjének
    public void sendShippingNotificationForReceiver(ParcelShippingNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getReceiverEmailAddress());
            message.setSubject("Csomagja megérkezett");
            message.setText("Kedves " + notification.getReceiverName() + "\n\n"
                    + "Az Ön " + notification.getUniqueParcelId() + " azonosítójú csomagja " + notification.getShippingDate()
                    + " " + notification.getShippingTime() + "-kor megérkezett." + "\n"
                    + "Itt tudja átvenni: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n"
                    + "Az átvételhez szükséges nyitókód: " + notification.getPickingUpCode() + "\n"
                    + "A csomag ára: " + notification.getPrice() + " Ft.\n"
                    + "A csomag feladója: " + notification.getSenderName() + "\n"
                    + "A feladás helye: " + notification.getSenderParcelLockerPostCode() + " " + notification.getSenderParcelLockerCity()
                    + " " + notification.getSenderParcelLockerStreet() + "\n"
            );
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }

    }

    //Átvétel utáni email értesítés küldése az átvevőnek
    public void sendPickingUpNotificationForReceiver(ParcelPickingUpNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getReceiverEmailAddress());
            message.setSubject("Csomag átvéve");
            message.setText("Kedves " + notification.getReceiverName() + "\n\n"
                    + "A(z) " + notification.getUniqueParcelId() + " azonosítójú csomagodat " + notification.getPickingUpDate()
                    + " " + notification.getPickingUpTime() + "-kor sikeresen átvetted." + "\n"
                    + "Átvétel helye: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n"
                    + "A csomag feladója: " + notification.getSenderName() + "\n"
            );
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }

    //Átvétel utáni email értesítés küldése a feladónak
    public void sendPickingUpNotificationForSender(ParcelPickingUpNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getSenderEmailAddress());
            message.setSubject("Csomag átvéve");
            message.setText("Kedves " + notification.getSenderName() + "\n\n"
                    + "A(z) " + notification.getUniqueParcelId() + " azonosítójú csomagodat " + notification.getPickingUpDate()
                    + " " + notification.getPickingUpTime() + "-kor sikeresen átvették." + "\n"
                    + "Átvétel helye: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n"
                    + "A csomag címzettje: " + notification.getReceiverName() + "\n"
            );
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }

    //Email értesítés, mután a felhasználó feladta a csomagját a weboldalról
    //Ez az email tartalmazza a feladási kódot
    public void parcelSendingFromWebPageNotification(ParcelSendingFromWebPageNotification notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getSenderEmailAddress());
            message.setSubject("Csomagfeladás weboldalról");
            message.setText("Kedves " + notification.getSenderName() + "\n\n"
                    + "Ön " + notification.getSendingDate() + " " + notification.getSendingTime() + "-kor " +
                    "sikeresen feladta a(z) " + notification.getUniqueParcelId() + " azonosítójú csomagját.\n"
                    + "Ez még csak egy előzetes feladás. A végleges feladáshoz, kérjük fáradjon el a feladási automatához és" +
                    " válassza a csomagfeladás feladási kóddal lehetőséget.\n"
                    + "A feladáshoz szükséges kód: " + notification.getSendingCode() + "\n"
                    + "A csomag feladási díját az automatánál tudja kifizetni bakkártyával.\n"
                    + "Itt tudja feladni a csomagot: " + notification.getSenderParcelLockerPostCode() + " " + notification.getSenderParcelLockerCity()
                    + " " + notification.getSenderParcelLockerStreet() + "\n"
                    + "Ide fog megérkezni: " + notification.getReceiverParcelLockerPostCode() + " " + notification.getReceiverParcelLockerCity()
                    + " " + notification.getReceiverParcelLockerStreet() + "\n");
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }

    //Email értesítés küldése, ami tartalmazza a második faktort a bejelentkezéshez
    public void sendSecondFactorCode(SecondFactorDTO notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getEmailAddress());
            message.setSubject("Kétfaktoros bejelentkezés");
            message.setText("A bejelentkezéshez szükséges kód: " + notification.getSecondFactorCode() + "\n\n");
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }


    //Új jelszó küldése email-ben
    //forgotPassword nevű topic
    public void forgotPassword(ForgotPasswordDTO notification) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("${spring.mail.username}");
            message.setTo(notification.getEmailAddress());
            message.setSubject("Új jelszó");
            message.setText("A bejelentkezéshez szükséges új jelszó: " + notification.getNewPassword() + "\n\n"
            + "Bejelentkezés után változtasd meg a jelszódat a profilom, jelszó módosítása menüpontban.");
            javaMailSender.send(message);

        }
        catch (Exception e){
            System.out.println("Email server error.");
        }
    }
}
