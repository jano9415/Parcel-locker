import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-courier-home',
  templateUrl: './courier-home.component.html',
  styleUrls: ['./courier-home.component.scss']
})
export class CourierHomeComponent {

  currenctCourier: Object | null = null;

  constructor(private cookieService: CookieService) {

  }

  ngOnInit(): void {

    //Futár objektum kiolvasása cookie-ből és visszaalakítás
    this.currenctCourier = JSON.parse(this.cookieService.get("currentCourier"));

  }

}
