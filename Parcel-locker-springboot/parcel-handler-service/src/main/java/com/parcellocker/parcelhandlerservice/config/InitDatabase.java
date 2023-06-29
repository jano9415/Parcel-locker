package com.parcellocker.parcelhandlerservice.config;

import com.netflix.discovery.converters.Auto;
import com.parcellocker.parcelhandlerservice.model.Address;
import com.parcellocker.parcelhandlerservice.model.Box;
import com.parcellocker.parcelhandlerservice.service.impl.AddressServiceImpl;
import com.parcellocker.parcelhandlerservice.service.impl.BoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class InitDatabase {

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private BoxServiceImpl boxService;


    //Címek létrehozása
    /*
    @Bean
    public void initAddress(){

        //Raktárak címei
        Address address1 = new Address();
        Address address2 = new Address();
        Address address3 = new Address();
        Address address4 = new Address();
        Address address5 = new Address();
        Address address6 = new Address();
        Address address7 = new Address();
        Address address8 = new Address();
        Address address9 = new Address();
        Address address10 = new Address();
        Address address11 = new Address();
        Address address12 = new Address();
        Address address13 = new Address();
        Address address14 = new Address();
        Address address15 = new Address();
        Address address16 = new Address();
        Address address17 = new Address();
        Address address18 = new Address();
        Address address19 = new Address();
        Address address20 = new Address();
        Address address21 = new Address();
        Address address22 = new Address();
        Address address23 = new Address();
        Address address24 = new Address();
        Address address25 = new Address();

        address1.setCity("Veszprém");
        address1.setCounty("Veszprém");
        address1.setPostCode(8100);
        address1.setStreet("Dózsa György utca 11");
        addressService.save(address1);

        address2.setCity("Székesfehérvár");
        address2.setCounty("Fejér");
        address2.setPostCode(8000);
        address2.setStreet("Budai út 2/b");
        addressService.save(address2);

        address3.setCity("Zalaegerszeg");
        address3.setCounty("Zala");
        address3.setPostCode(8900);
        address3.setStreet("Ady Endre utca 2");
        addressService.save(address3);

        address4.setCity("Kaposvár");
        address4.setCounty("Somogy");
        address4.setPostCode(7400);
        address4.setStreet("Fő út 15");
        addressService.save(address4);

        address5.setCity("Szombathely");
        address5.setCounty("Vas");
        address5.setPostCode(9700);
        address5.setStreet("Körmendi út 21");
        addressService.save(address5);

        //Veszprém megyei automaták
        address6.setCity("Veszprém");
        address6.setCounty("Veszprém");
        address6.setPostCode(8100);
        address6.setStreet("Pápai út 32");
        addressService.save(address6);

        address7.setCity("Veszprém");
        address7.setCounty("Veszprém");
        address7.setPostCode(8100);
        address7.setStreet("Jutasi út 4");
        addressService.save(address7);

        address8.setCity("Várpalota");
        address8.setCounty("Veszprém");
        address8.setPostCode(8100);
        address8.setStreet("Újlaky út 8");
        addressService.save(address8);

        address9.setCity("Várpalota");
        address9.setCounty("Veszprém");
        address9.setPostCode(8100);
        address9.setStreet("Árpád utca 1");
        addressService.save(address9);

        //Fejér megyei automaták
        address10.setCity("Székesfehérvár");
        address10.setCounty("Fejér");
        address10.setPostCode(8000);
        address10.setStreet("Úrhidai út 6");
        addressService.save(address10);

        address11.setCity("Zámoly");
        address11.setCounty("Fejér");
        address11.setPostCode(8000);
        address11.setStreet("Nefelejcs utca 21");
        addressService.save(address11);

        address12.setCity("Lovasberény");
        address12.setCounty("Fejér");
        address12.setPostCode(8000);
        address12.setStreet("Zrínyi utca 1");
        addressService.save(address12);

        address13.setCity("Velence");
        address13.setCounty("Fejér");
        address13.setPostCode(8000);
        address13.setStreet("Ősz utca 13/a");
        addressService.save(address13);

        //Zala megyei automaták
        address14.setCity("Lenti");
        address14.setCounty("Zala");
        address14.setPostCode(8900);
        address14.setStreet("Béke utca 20");
        addressService.save(address14);

        address15.setCity("Zalavár");
        address15.setCounty("Zala");
        address15.setPostCode(8900);
        address15.setStreet("József Attila utca 17");
        addressService.save(address15);

        address16.setCity("Zalaegerszeg");
        address16.setCounty("Zala");
        address16.setPostCode(8900);
        address16.setStreet("Görcseji út 6");
        addressService.save(address16);

        address17.setCity("Teskánd");
        address17.setCounty("Zala");
        address17.setPostCode(8900);
        address17.setStreet("Rózsa utca 9");
        addressService.save(address17);

        //Somogy megyei automaták
        address18.setCity("Marcali");
        address18.setCounty("Somogy");
        address18.setPostCode(7400);
        address18.setStreet("Nagypincei út 15");
        addressService.save(address18);

        address19.setCity("Fonyód");
        address19.setCounty("Somogy");
        address19.setPostCode(7400);
        address19.setStreet("Domb utca 27");
        addressService.save(address19);

        address20.setCity("Igal");
        address20.setCounty("Somogy");
        address20.setPostCode(7400);
        address20.setStreet("Szent István út 8");
        addressService.save(address20);

        address21.setCity("Kaposvár");
        address21.setCounty("Somogy");
        address21.setPostCode(7400);
        address21.setStreet("Benedek Elek utca 20");
        addressService.save(address21);

        //Vas megyei automaták
        address22.setCity("Szombathely");
        address22.setCounty("Vas");
        address22.setPostCode(9700);
        address22.setStreet("Szőlős út 4");
        addressService.save(address22);

        address23.setCity("Sárvár");
        address23.setCounty("Vas");
        address23.setPostCode(9700);
        address23.setStreet("Székely út 13");
        addressService.save(address23);

        address24.setCity("Körmend");
        address24.setCounty("Vas");
        address24.setPostCode(9700);
        address24.setStreet("Dankó Pista utca 24");
        addressService.save(address24);

        address25.setCity("Szentgotthárd");
        address25.setCounty("Vas");
        address25.setPostCode(9700);
        address25.setStreet("Zöld mező utca 5");
        addressService.save(address25);
    }
     */


    //Rekeszek létrehozása
    /*
    @Bean
    public void initBox(){

        //200x70x50
        Box box1 = new Box();
        box1.setBoxNumber(1);
        box1.setMaxHeight(50);
        box1.setMaxLength(200);
        box1.setMaxWeight(5);
        box1.setMaxWidth(70);
        boxService.save(box1);

        Box box2 = new Box();
        box2.setBoxNumber(2);
        box2.setMaxHeight(50);
        box2.setMaxLength(200);
        box2.setMaxWeight(5);
        box2.setMaxWidth(70);
        boxService.save(box2);

        Box box3 = new Box();
        box3.setBoxNumber(3);
        box3.setMaxHeight(50);
        box3.setMaxLength(200);
        box3.setMaxWeight(5);
        box3.setMaxWidth(70);
        boxService.save(box3);

        Box box4 = new Box();
        box4.setBoxNumber(4);
        box4.setMaxHeight(50);
        box4.setMaxLength(200);
        box4.setMaxWeight(5);
        box4.setMaxWidth(70);
        boxService.save(box4);

        Box box5 = new Box();
        box5.setBoxNumber(5);
        box5.setMaxHeight(50);
        box5.setMaxLength(200);
        box5.setMaxWeight(5);
        box5.setMaxWidth(70);
        boxService.save(box5);

        Box box6 = new Box();
        box6.setBoxNumber(6);
        box6.setMaxHeight(50);
        box6.setMaxLength(200);
        box6.setMaxWeight(5);
        box6.setMaxWidth(70);
        boxService.save(box6);

        Box box7 = new Box();
        box7.setBoxNumber(7);
        box7.setMaxHeight(50);
        box7.setMaxLength(200);
        box7.setMaxWeight(5);
        box7.setMaxWidth(70);
        boxService.save(box7);

        Box box8 = new Box();
        box8.setBoxNumber(8);
        box8.setMaxHeight(50);
        box8.setMaxLength(200);
        box8.setMaxWeight(5);
        box8.setMaxWidth(70);
        boxService.save(box8);

        Box box9 = new Box();
        box9.setBoxNumber(9);
        box9.setMaxHeight(50);
        box9.setMaxLength(200);
        box9.setMaxWeight(5);
        box9.setMaxWidth(70);
        boxService.save(box9);

        Box box10 = new Box();
        box10.setBoxNumber(10);
        box10.setMaxHeight(50);
        box10.setMaxLength(200);
        box10.setMaxWeight(5);
        box10.setMaxWidth(70);
        boxService.save(box10);

        //200x120x100
        Box box11 = new Box();
        box11.setBoxNumber(11);
        box11.setMaxHeight(100);
        box11.setMaxLength(200);
        box11.setMaxWeight(5);
        box11.setMaxWidth(120);
        boxService.save(box11);

        Box box12 = new Box();
        box12.setBoxNumber(12);
        box12.setMaxHeight(100);
        box12.setMaxLength(200);
        box12.setMaxWeight(5);
        box12.setMaxWidth(120);
        boxService.save(box12);

        Box box13 = new Box();
        box13.setBoxNumber(13);
        box13.setMaxHeight(100);
        box13.setMaxLength(200);
        box13.setMaxWeight(5);
        box13.setMaxWidth(120);
        boxService.save(box13);

        Box box14 = new Box();
        box14.setBoxNumber(14);
        box14.setMaxHeight(100);
        box14.setMaxLength(200);
        box14.setMaxWeight(5);
        box14.setMaxWidth(120);
        boxService.save(box14);

        Box box15 = new Box();
        box15.setBoxNumber(15);
        box15.setMaxHeight(100);
        box15.setMaxLength(200);
        box15.setMaxWeight(5);
        box15.setMaxWidth(120);
        boxService.save(box15);

        Box box16 = new Box();
        box16.setBoxNumber(16);
        box16.setMaxHeight(100);
        box16.setMaxLength(200);
        box16.setMaxWeight(5);
        box16.setMaxWidth(120);
        boxService.save(box16);

        Box box17 = new Box();
        box17.setBoxNumber(17);
        box17.setMaxHeight(100);
        box17.setMaxLength(200);
        box17.setMaxWeight(5);
        box17.setMaxWidth(120);
        boxService.save(box17);

        Box box18 = new Box();
        box18.setBoxNumber(18);
        box18.setMaxHeight(100);
        box18.setMaxLength(200);
        box18.setMaxWeight(5);
        box18.setMaxWidth(120);
        boxService.save(box18);

        Box box19 = new Box();
        box19.setBoxNumber(19);
        box19.setMaxHeight(100);
        box19.setMaxLength(200);
        box19.setMaxWeight(5);
        box19.setMaxWidth(120);
        boxService.save(box19);

        Box box20 = new Box();
        box20.setBoxNumber(20);
        box20.setMaxHeight(100);
        box20.setMaxLength(200);
        box20.setMaxWeight(5);
        box20.setMaxWidth(120);
        boxService.save(box20);

        //200x170x150
        Box box21 = new Box();
        box21.setBoxNumber(21);
        box21.setMaxHeight(150);
        box21.setMaxLength(200);
        box21.setMaxWeight(5);
        box21.setMaxWidth(170);
        boxService.save(box21);

        Box box22 = new Box();
        box22.setBoxNumber(22);
        box22.setMaxHeight(150);
        box22.setMaxLength(200);
        box22.setMaxWeight(5);
        box22.setMaxWidth(170);
        boxService.save(box22);

        Box box23 = new Box();
        box23.setBoxNumber(23);
        box23.setMaxHeight(150);
        box23.setMaxLength(200);
        box23.setMaxWeight(5);
        box23.setMaxWidth(170);
        boxService.save(box23);

        Box box24 = new Box();
        box24.setBoxNumber(24);
        box24.setMaxHeight(150);
        box24.setMaxLength(200);
        box24.setMaxWeight(5);
        box24.setMaxWidth(170);
        boxService.save(box24);

        Box box25 = new Box();
        box25.setBoxNumber(25);
        box25.setMaxHeight(150);
        box25.setMaxLength(200);
        box25.setMaxWeight(5);
        box25.setMaxWidth(170);
        boxService.save(box25);

        Box box26 = new Box();
        box26.setBoxNumber(26);
        box26.setMaxHeight(150);
        box26.setMaxLength(200);
        box26.setMaxWeight(5);
        box26.setMaxWidth(170);
        boxService.save(box26);

        Box box27 = new Box();
        box27.setBoxNumber(27);
        box27.setMaxHeight(150);
        box27.setMaxLength(200);
        box27.setMaxWeight(5);
        box27.setMaxWidth(170);
        boxService.save(box27);

        Box box28 = new Box();
        box28.setBoxNumber(28);
        box28.setMaxHeight(150);
        box28.setMaxLength(200);
        box28.setMaxWeight(5);
        box28.setMaxWidth(170);
        boxService.save(box28);

        Box box29 = new Box();
        box29.setBoxNumber(29);
        box29.setMaxHeight(150);
        box29.setMaxLength(200);
        box29.setMaxWeight(5);
        box29.setMaxWidth(170);
        boxService.save(box29);

        Box box30 = new Box();
        box30.setBoxNumber(30);
        box30.setMaxHeight(150);
        box30.setMaxLength(200);
        box30.setMaxWeight(5);
        box30.setMaxWidth(170);
        boxService.save(box30);

    }
     */

}
