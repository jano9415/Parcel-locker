import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';
import { CookieService } from 'ngx-cookie-service';
import { StringResponse } from '../Payload/string-response';

@Injectable({
  providedIn: 'root'
})
export class ParcelLockerService {

  private API_URL = "http://localhost:8080/parcelhandler/parcellocker/";

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

  //Csomag automaták lekérése kiválasztásra
  getParcelLockersForChoice(): Observable<Array<ParcelLockerDTO>> {
    return this.httpClient.get<Array<ParcelLockerDTO>>(`${this.API_URL + "getparcellockersforchoice"}`);
  }

  //Csomag küldése feladási kód nélkül
  sendParcelWithoutCode(parcelSendingFormValues: Object): Observable<any> {
    this.getSenderParcelLockerId();
    return this.httpClient.post<any>(`${this.API_URL + "sendparcelwithoutcode"}/${this.senderParcelLockerId}`, parcelSendingFormValues);
  }

  //Automata tele van?
  isParcelLockerFull(): Observable<StringResponse> {
    this.getSenderParcelLockerId();
    return this.httpClient.get<StringResponse>(`${this.API_URL + "isparcellockerfull"}/${this.senderParcelLockerId}`);
  }

  //Kicsi rekeszek tele vannak?
  areSmallBoxesFull(): Observable<StringResponse> {
    this.getSenderParcelLockerId();
    return this.httpClient.get<StringResponse>(`${this.API_URL + "aresmallboxesfull"}/${this.senderParcelLockerId}`);
  }
}
