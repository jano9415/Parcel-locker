import { Component } from '@angular/core';
import { AuthService } from '../Service/auth.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


//Javascript függvények meghívása az assets mappából
declare function serialRead(): string;
declare function getUniqueCourierId(): string;



@Component({
  selector: 'app-courier-login',
  templateUrl: './courier-login.component.html',
  styleUrls: ['./courier-login.component.scss']
})
export class CourierLoginComponent {

  password: string = "";

  message: string = "";

  constructor(private authService: AuthService, private router: Router,
    private cookieService: CookieService) {


  }

  ngOnInit(): void {
    serialRead();

  }

  courierLogin(): void {
    this.password = getUniqueCourierId();

    if (this.password) {

      this.authService.courierLogin(this.password).subscribe({
        next: (response) => {

          if (response.message === "notFound") {
            this.message = "Hibás azonosító. Kattints a kezdőlapra, frissítsd az oldalt, majd kattints a bejelentkezésre" +
             " és próbálj meg újra bejelentkezni.";
          }
          else {

            const currentCourier = {
              token: response.token,
              tokenType: response.tokenType,
              userId: response.userId,
              emailAddress: response.emailAddress,
              firstName: response.firstName,
              lastName: response.lastName,
              roles: response.roles
            }

            //Futár objektum szerializálása és mentés cookie-ban
            const serializedCourier = JSON.stringify(currentCourier);
            this.cookieService.set("currentCourier", serializedCourier);

            //Futár objektum kiolvasása cookie-ből és visszaalakítás
            const deserializedCourier = JSON.parse(this.cookieService.get("currentCourier"));

            //Navigáció a home oldalra és oldal frissítése
            this.router.navigateByUrl("courierhome", { skipLocationChange: false }).then(() => {
              window.location.reload();
            });


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
    else {
      this.message = "Érintsd a kártyád az olvasóhoz.";
    }
  }


}
