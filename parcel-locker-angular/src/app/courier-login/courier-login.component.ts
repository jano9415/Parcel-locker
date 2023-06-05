import { Component } from '@angular/core';


declare function greet():void;
declare function serialRead():string;




@Component({
  selector: 'app-courier-login',
  templateUrl: './courier-login.component.html',
  styleUrls: ['./courier-login.component.scss']
})
export class CourierLoginComponent {

  uId: string = "";

  constructor(){
    greet();

    
    console.log("Angular component: " + serialRead());
    

  }

  ngOnInit(): void {
    

  }

}
