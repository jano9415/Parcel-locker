import { Component } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login-menu',
  templateUrl: './login-menu.component.html',
  styleUrls: ['./login-menu.component.scss']
})
export class LoginMenuComponent {


  token: string = "";

  constructor(private cookieService: CookieService, private router: Router) {

  }

  ngOnInit(): void {

    //Jwt token lekérése cookie-ből
    try {
      this.token = JSON.parse(this.cookieService.get("currentCourier")).token;
    } catch (error) {
      
    }


  }

  //Kijelentkezés
  //Bejelentkezett futár objektum törlése a cookie-ből
  logOut() {

    this.cookieService.set("currentCourier", "");
    this.router.navigateByUrl("/home");
    window.location.reload();

  }

}
