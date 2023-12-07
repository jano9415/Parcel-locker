import { Component } from '@angular/core';

//Javascript függvények meghívása az assets mappából
declare function serialWrite(boxNumbers: String): void;
declare function connectToArduino(): void;

@Component({
  selector: 'app-open-boxes',
  templateUrl: './open-boxes.component.html',
  styleUrls: ['./open-boxes.component.scss']
})
export class OpenBoxesComponent {

  boxNumbers: Array<String> = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
    "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
  ];

  selectedBoxNumber: String = this.boxNumbers[0];

  constructor() {

  }

  ngOnInit(): void {

    //Kapcsolódás az arduino-hoz
    connectToArduino();

  }

  //Kiválasztott rekesz nyitása
  openSelectedBox(): void {
    
    serialWrite(this.selectedBoxNumber);
  }

}
