import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParcelService {

  private API_URL = "http://localhost:8080/parcelhandler/parcel/";

  private senderParcelLockerId: string = "";

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

  //Header összeállítása jwt token nélkül
  getOptionsWithoutToken(): any {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    const options = { headers: headers };
    return options;
  }

  //Header összeállítása ami tartalmazza a jwt tokent
  getOptionsWithToken(): any {
    try {
      const token = JSON.parse(this.cookieService.get("currentCourier")).token;
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `${token}`
      });
      const options = { headers: headers };
      return options;

    } catch (error) {
      console.log(error + ": Getting token from cookie is unsuccess");
    }

  }

  //Feladási automata id lekérése local storage-ról
  getSenderParcelLockerId() {
    const senderParcelLockerId = localStorage.getItem('senderParcelLockerId');
    this.senderParcelLockerId = senderParcelLockerId !== null ? senderParcelLockerId : '';
  }


  //Kérések a szerver felé

  //Csomag küldése feladási kód nélkül
  sendParcelWithoutCode(parcelSendingFormValues: Object): Observable<any> {
    this.getSenderParcelLockerId();
    return this.httpClient.post<any>(`${this.API_URL + "sendparcelwithoutcode"}/${this.senderParcelLockerId}`, parcelSendingFormValues);
  }
}
