import { Component } from '@angular/core';
import { AuthService } from '../Service/auth.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';


declare function serialRead(): string;
declare function getUniqueCourierId(): string;



@Component({
  selector: 'app-courier-login',
  templateUrl: './courier-login.component.html',
  styleUrls: ['./courier-login.component.scss']
})
export class CourierLoginComponent {

  password: string = "";

  constructor(private authService: AuthService, private router: Router,
    private cookieService: CookieService) {


  }

  ngOnInit(): void {
    serialRead();

  }

  courierLogin(): void {
    this.password = getUniqueCourierId();

    if (this.password) {
      this.authService.courierLogin(this.password).subscribe(data => {

        const currentCourier = {
          token: data.token,
          tokenType: data.tokenType,
          userId: data.userId,
          emailAddress: data.emailAddress,
          firstName: data.firstName,
          lastName: data.lastName,
          roles: data.roles
        }

        //Futár objektum szerializálása és mentés cookie-ban
        const serializedCourier = JSON.stringify(currentCourier);
        this.cookieService.set("currentCourier", serializedCourier);

        //Futár objektum kiolvasása cookie-ből és visszaalakítás
        const deserializedCourier = JSON.parse(this.cookieService.get("currentCourier"));

        this.router.navigateByUrl("/home");
        //window.location.reload();
        
      })
    }
  }

}
