import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private router: Router){
    //window.location.reload();

  }

  ngOnInit(): void{
    
  }

  

  //Route a csomag küldése kóddal oldalra
  parcelSendingWithCode(): void{
    this.router.navigate(["parcelsendingwithcode"]);
  }

  //Route a csomag küldése kód nélkül oldalra
  parcelSendingWithoutCode(): void{
    this.router.navigate(["parcelsendingwithoutcode"]);
  }

  //Route a csomag átvétele oldalra
  parcelPickingUp(): void{
    this.router.navigate(["parcelpickingup"]);
  }

}
