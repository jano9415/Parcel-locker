import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponse } from '../Payload/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API_URL = "http://localhost:8080/auth/";

  private senderParcelLockerId: string = "";

  constructor(private httpClient: HttpClient) { }

  //Feladási automata id lekérése local storage-ról
  getSenderParcelLockerId() {
    const senderParcelLockerId = localStorage.getItem('senderParcelLockerId');
    this.senderParcelLockerId = senderParcelLockerId !== null ? senderParcelLockerId : '';
  }


  /*
  //Futár bejelentkezés
  //Paraméteres példa miatt itt marad még
  courierLogin(uniqueCourierId: string): Observable<LoginResponse> {
    //Paraméter összeállítása
    const parameters = new HttpParams()
      .append('uniqueCourierId', uniqueCourierId);



    return this.httpClient.post<LoginResponse>(`${this.API_URL + "courierlogin"}`, {}, {
      params: parameters
    })
  }
  */


  //Futár bejelentkezés
  //Rfid uid: 07 6205 26
  courierLogin(password: string): Observable<any> {
    this.getSenderParcelLockerId();
    return this.httpClient.post<any>(`${this.API_URL + "courierlogin"}/${this.senderParcelLockerId}`, { password });
  }

}
