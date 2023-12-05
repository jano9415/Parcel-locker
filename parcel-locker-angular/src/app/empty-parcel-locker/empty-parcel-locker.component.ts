import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { ParcelService } from '../Service/parcel.service';
import { MatTableDataSource } from '@angular/material/table';

//Javascript függvények meghívása az assets mappából
declare function serialWrite(boxNumbers: String): void;
declare function connectToArduino(): void;

@Component({
  selector: 'app-empty-parcel-locker',
  templateUrl: './empty-parcel-locker.component.html',
  styleUrls: ['./empty-parcel-locker.component.scss']
})
export class EmptyParcelLockerComponent {

  currenctCourier: any | null = null;

  parcelsForShipping: Array<Object> | null = null;

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

    connectToArduino();

    //Futár objektum kiolvasása cookie-ből és visszaalakítás
    try {

      this.currenctCourier = JSON.parse(this.cookieService.get("currentCourier"));
    } catch (error) {
      console.log(error + ": Getting token from cookie is unsuccess");
    }


    //Csomagok lekérése és megjelenítése, amik készen állnak az elszállításra
    this.parcelService.getParcelsForShipping().subscribe({
      next: (response) => {

        if (response.length === 0) {
          this.noParcelMessage = "Nincs elszállításra váró csomag";
          this.sendButtonVisible = false;
        }
        else {
          this.parcelsForShipping = response;
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

  //Automata kiürítése. Elszállításra váró csomagok átkerülnek a futárhoz
  //A válasz a kinyíló rekeszek azonosítója
  emptyParcelLocker(): void {

    this.parcelService.emptyParcelLocker(this.currenctCourier.emailAddress).subscribe({
      next: (response) => {
        let boxNumbers = "";
        
        serialWrite(response);
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
