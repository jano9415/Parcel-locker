import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-courier-home',
  templateUrl: './courier-home.component.html',
  styleUrls: ['./courier-home.component.scss']
})
export class CourierHomeComponent {

  currenctCourier: Object | null = null;

  constructor(private cookieService: CookieService, private router: Router) {

  }

  ngOnInit(): void {

    //Futár objektum kiolvasása cookie-ből és visszaalakítás
    this.currenctCourier = JSON.parse(this.cookieService.get("currentCourier"));

  }

  //Route az automata kiürítése oldalra
  emptyParcelLocker(): void {
    this.router.navigate(["emptyparcellocker"]);
  }

  //Route az automata feltöltése oldalra
  fillParcelLocker(): void {
    this.router.navigate(["fillparcellocker"]);
  }

  //Route a rekeszek nyitása manuálisan oldalra
  openBoxesByManual(): void {
    this.router.navigate(["openBoxes"]);
  }

}
