import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  senderParcelLockerPostCode: string = "";

  senderParcelLockerCity: string = "";

  senderParcelLockerStreet: string = "";

  constructor(private router: Router) {

  }

  ngOnInit(): void {

    //Feladási csomag automata változói
    //Lekérés local storage-ról
    const postCode = localStorage.getItem('senderParcelLockerPostCode');
    const city = localStorage.getItem('senderParcelLockerCity');
    const street = localStorage.getItem('senderParcelLockerStreet');

    this.senderParcelLockerPostCode = postCode !== null ? postCode: '';
    this.senderParcelLockerCity = city !== null ? city: '';
    this.senderParcelLockerStreet = street !== null ? street: '';


  }



  //Route a csomag küldése kóddal oldalra
  parcelSendingWithCode(): void {
    this.router.navigate(["parcelsendingwithcode"]);
  }

  //Route a csomag küldése kód nélkül oldalra
  parcelSendingWithoutCode(): void {
    this.router.navigate(["parcelsendingwithoutcode"]);
  }

  //Route a csomag átvétele oldalra
  parcelPickingUp(): void {
    this.router.navigate(["parcelpickingup"]);
  }

}
