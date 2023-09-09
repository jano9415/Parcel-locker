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
    //Minden előzetesen feladott csomag lejárati ideje a lejárat napja + 3 nap. A lejárati idő pedig 5:29
    //Így az aznap lejárt csomagokat mindet ki fogja törölni a rendszer
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

                //Ha van feladási lejárati dátum és időpont és a csomag még nincs elhelyezve
                if(parcel.getSendingExpirationDate() != null && parcel.getSendingExpirationTime() != null &&
                parcel.isPlaced() == false){

                    LocalDate expirationDate = parcel.getSendingExpirationDate();
                    LocalTime expirationTime = parcel.getSendingExpirationTime();

                    //Ha a jelenlegi dátum és a lejárati dátum megegyezik, akkor a csomag lejárt 5:29-kor
                    // De azért ellenőrzöm a lejárati időpont és az aktuális időpont viszonyát
                    if(currentDate.isEqual(expirationDate) && currentTime.isAfter(expirationTime)){
                        //Kapcsolótábla frissítése
                        parcel.setParcelLocker(null);
                        parcelService.save(parcel);
                        //Csomag törlése az adatbázisból
                        parcelService.delete(parcel);
                        //Itt még lehetne küldeni egy email értesítést erről

                    }

                }


            }
        }
    }
}
