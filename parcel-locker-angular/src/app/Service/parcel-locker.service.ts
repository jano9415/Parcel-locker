import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class ParcelLockerService {

  private API_URL = "http://localhost:8080/parcelhandler/parcellocker/";

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

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

  //Csomag automaták lekérése kiválasztásra
  getParcelLockersForChoice(): Observable<Array<ParcelLockerDTO>>{
    return this.httpClient.get<Array<ParcelLockerDTO>>(`${this.API_URL + "getparcellockersforchoice"}`);
  }

  //Csomag küldése feladási kód nélkül
  sendParcelWithoutCode(parcelSendingFormValues: Object): Observable<any>{
    return this.httpClient.post<any>(`${this.API_URL + "sendparcelwithoutcode"}` , parcelSendingFormValues);
  }
}
