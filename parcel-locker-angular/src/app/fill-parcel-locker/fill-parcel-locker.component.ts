import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ParcelService } from '../Service/parcel.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-fill-parcel-locker',
  templateUrl: './fill-parcel-locker.component.html',
  styleUrls: ['./fill-parcel-locker.component.scss']
})
export class FillParcelLockerComponent {

  currenctCourier: any | null = null;

  parcelsForParcelLocker: Array<Object> | null = null;

  displayedColumns: string[] = ['uniqueParcelId', 'price', 'senderParcelLockerPostCode', 'receiverParcelLockerPostCode',
  'boxNumber'];

  dataSourceForTable!: MatTableDataSource<any>;

  constructor(private cookieService: CookieService, private router: Router,
    private parcelService: ParcelService) {

  }

  ngOnInit(): void {

    //Futár objektum kiolvasása cookie-ből és visszaalakítás
    try {

      this.currenctCourier = JSON.parse(this.cookieService.get("currentCourier"));
    } catch (error) {
      console.log(error + ": Getting token from cookie is unsuccess");
    }

    //Csomagok lekérése és megjelenítése, amiket el lehet helyezni ebben az automatában
    this.parcelService.getParcelsForParcelLocker(this.currenctCourier.emailAddress).subscribe({
      next: (response) => {
        this.parcelsForParcelLocker = response;
        this.dataSourceForTable = new MatTableDataSource(response);
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        console.log("Compelete");
      }
    })



  }

  //Automata feltöltése
  //A válasz a kinyíló rekeszek azonosítója
  fillParcelLocker(): void{
    this.parcelService.fillParcelLocker(this.currenctCourier.emailAddress).subscribe({
      next: (response) => {
        console.log(response);
        window.location.reload();
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        console.log("Compelete");
      }
    })
  }

}
