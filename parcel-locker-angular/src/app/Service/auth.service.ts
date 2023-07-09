import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponse } from '../Payload/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API_URL = "http://localhost:8080/auth/";

  constructor(private httpClient: HttpClient) { }


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
  courierLogin(password: string): Observable<LoginResponse> {


    return this.httpClient.post<LoginResponse>(`${this.API_URL + "courierlogin"}`, {password});
  }
}
