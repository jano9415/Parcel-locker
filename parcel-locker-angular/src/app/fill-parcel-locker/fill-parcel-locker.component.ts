import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ParcelService } from '../Service/parcel.service';
import { MatTableDataSource } from '@angular/material/table';


//Javascript függvények meghívása az assets mappából
declare function serialWrite(boxNumbers: String): void;
declare function connectToArduino(): void;

@Component({
  selector: 'app-fill-parcel-locker',
  templateUrl: './fill-parcel-locker.component.html',
  styleUrls: ['./fill-parcel-locker.component.scss']
})
export class FillParcelLockerComponent {

  currenctCourier: any | null = null;

  parcelsForParcelLocker: Array<any> | null = null;

  displayedColumns: string[] = ['uniqueParcelId', 'senderParcelLockerPostCode', 'receiverParcelLockerPostCode',
    'boxNumber'];

  dataSourceForTable!: MatTableDataSource<any>;

  boxNumberMessages!: Array<any>;

  noParcelMessage: string = "";

  sendButtonVisible: boolean = false;

  constructor(private cookieService: CookieService, private router: Router,
    private parcelService: ParcelService) {

  }

  ngOnInit(): void {

    //Kapcsolódás az arduino-hoz
    connectToArduino();

    //Futár objektum kiolvasása cookie-ből és visszaalakítás
    try {

      this.currenctCourier = JSON.parse(this.cookieService.get("currentCourier"));
    } catch (error) {
      console.log(error + ": Getting token from cookie is unsuccess");
    }

    //Csomagok lekérése és megjelenítése, amiket el lehet helyezni ebben az automatában
    this.parcelService.getParcelsForParcelLocker(this.currenctCourier.emailAddress).subscribe({
      next: (response) => {

        if (response.length === 0) {
          this.noParcelMessage = "Ehhez az automatához nincs csomagod, vagy az automata tele van.";
          this.sendButtonVisible = false;
        }
        else {
          this.parcelsForParcelLocker = response;
          this.dataSourceForTable = new MatTableDataSource(response);
          this.sendButtonVisible = true;
        }
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
  fillParcelLocker(): void {
    this.parcelService.fillParcelLocker(this.currenctCourier.emailAddress).subscribe({
      next: (response) => {
        //Rekeszek nyitása, adatok küldése soros porton az arduino-nak
        //A válaszban lévő rekesz számokat összefűzöm egy string-be, és ezt a stringet küldöm ki soros porton az arudino-nak
        let boxNumbers = response.map((item: { boxNumber: any; }) => item.boxNumber).join('');
        serialWrite(boxNumbers);

        this.boxNumberMessages = response;
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
