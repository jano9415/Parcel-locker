import { Component } from '@angular/core';
import { AuthService } from '../Service/auth.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


//Javascript függvények meghívása az assets mappából
declare function serialRead(): string;
declare function serialRead2(): string;
declare function connectToArduino(): void;
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
    
    //Kapcsolódás az arduino-hoz
    connectToArduino();
  }

  proba1(): void {
    serialRead2();
  }

  /*
  courierLogin(): void {

    serialRead2();

    setTimeout(() => {

    }, 2000);

    this.password = getUniqueCourierId();

    if (this.password) {

      this.authService.courierLogin(this.password).subscribe({
        next: (response) => {
          //Hibás azonosító
          if (response.message === "notFound") {
            this.message = "Hibás azonosító. Kattints a kezdőlapra, frissítsd az oldalt, majd kattints a bejelentkezésre" +
              " és próbálj meg újra bejelentkezni.";
          }
          //Nincs jogosultság az automatához
          if (response.message === "notEligible") {
            this.message = "Nincs jogosultságod ehhez az automatához.";
          }
          //Sikeres bejelentkezés
          if (response.token) {
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
  */

  courierLogin(): void {

    //Soros port olvasása.
    serialRead2();

    //Előbb kiolvasom a soros port tartalmát, ahol a kapott rfid azonosítót elmentem egy globális változóba.
    //Az olvasás és a globális változó lekérése is a "Bejelentkezés" gombnyomásra történik.
    //Ezért időzítőre van szükség. Megnyomom a "Bejelentkezés" gombot -> kiolvasom a soros port tartalmát -> ezután várok 500 ms-ot,
    //hogy megtörténjen az olasás, és elmentse az rfid azonosítót a globális változóba -> az 500 ms után kiolvasom a globális
    //uniqueCourierId változót, amit már így el tudok küldeni a szervernek
    setTimeout(() => {
      this.password = getUniqueCourierId();

      if (this.password) {

        this.authService.courierLogin(this.password).subscribe({
          next: (response) => {
            //Hibás azonosító
            if (response.message === "notFound") {
              this.message = "Hibás azonosító. Kattints a kezdőlapra, frissítsd az oldalt, majd kattints a bejelentkezésre" +
                " és próbálj meg újra bejelentkezni.";
            }
            //Nincs jogosultság az automatához
            if (response.message === "notEligible") {
              this.message = "Nincs jogosultságod ehhez az automatához.";
            }
            //Sikeres bejelentkezés
            if (response.token) {
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

    }, 500);

  }


}
