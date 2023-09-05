package com.parcellocker.parcelhandlerservice.schedule;

import com.parcellocker.parcelhandlerservice.model.Parcel;
import com.parcellocker.parcelhandlerservice.model.ParcelLocker;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelLockerServiceImpl;
import com.parcellocker.parcelhandlerservice.service.impl.ParcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@EnableScheduling
@Component
public class ScheduledTasks {

    @Autowired
    private ParcelLockerServiceImpl parcelLockerService;

    @Autowired
    private ParcelServiceImpl parcelService;

    //Online feladott csomagok, amiket nem helyeztek el időben az automatában, és azoknak lejárt a feladási határideje
    //A függvény minden reggel 5:30-kor lefut
    //Transactional nélkül hibára fut
    @Scheduled(cron = "0 30 5 * * *") // Cron kifejezés: másodperc perc óra nap hónap nap a hét sorrendben
    @Transactional
    public void deleteBoxReservations() {

        //Nem az adatbázisban megtalálható összes csomagot ellenőrzöm,
        //hanem minden automatának megnézem a csomagjait
        for(ParcelLocker parcelLocker : parcelLockerService.findAll()){
            for(Parcel parcel : parcelLocker.getParcels()){

                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();

                if(parcel.getSendingExpirationDate() != null && parcel.getSendingExpirationTime() != null){

                    LocalDate expirationDate = parcel.getSendingExpirationDate();
                    LocalTime expirationTime = parcel.getSendingExpirationTime();

                    //Ha a jelenlegi dátum és a lejárati dátum megegyezik, akkor az időpontokat kell megvizsgálni
                    if(currentDate.isEqual(expirationDate) && currentTime.isAfter(expirationTime)){
                        //Kapcsolótábla frissítése
                        parcel.setParcelLocker(null);
                        parcelService.save(parcel);
                        //Csomag törlése az adatbázisból
                        parcelService.delete(parcel);
                        //Itt még lehetne küldeni egy email értesítést erről

                    }
                    //Ha a jelenlegi dátum nagyobb, mint a lejárati dátum
                    if(currentDate.isAfter(expirationDate)){
                        //Kapcsolótábla frissítése
                        parcel.setParcelLocker(null);
                        parcelService.save(parcel);
                        //Csomag törlése az adatbázisból
                        parcelService.delete(parcel);

                    }

                }


            }
        }
    }
}
