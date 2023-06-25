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

  uniqueCourierId: string = "";

  constructor(private authService: AuthService, private router: Router,
    private cookieService: CookieService) {


  }

  ngOnInit(): void {
    serialRead();

  }

  courierLogin(): void {
    this.uniqueCourierId = getUniqueCourierId();

    if (this.uniqueCourierId) {
      this.authService.courierLogin(this.uniqueCourierId).subscribe(data => {
        
        // Token mentése a cookie-ba
        const token = data.token;
        this.cookieService.set('token', token, 7); // Az 5 utolsó paraméter a lejárati idő (napokban)

        const emailAddress = data.emailAddress;
        this.cookieService.set("emailAddress", emailAddress);

        // Token lekérése a cookie-ból
        const storedToken = this.cookieService.get('token');
        console.log('Stored token:', storedToken);

        console.log(this.cookieService.get("emailAddress"));
        
      })
    }
  }

}
