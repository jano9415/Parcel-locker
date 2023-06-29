import { Component } from '@angular/core';
import { ParcelLockerService } from '../Service/parcel-locker.service';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';


@Component({
  selector: 'app-choose-parcel-locker',
  templateUrl: './choose-parcel-locker.component.html',
  styleUrls: ['./choose-parcel-locker.component.scss']
})
export class ChooseParcelLockerComponent {


  parcelLockers: Array<ParcelLockerDTO> = [];

  selectedParcelLockerId: number = 0;

  constructor(private parcelLockerService: ParcelLockerService){

  }

  ngOnInit(): void{
    //Automaták lekérése kiválasztásra
    this.parcelLockerService.getParcelLockersForChoice().subscribe(data => {
      this.parcelLockers = data;
    })

  }

  //Kiválasztott automata mentése local storage-ra
  sendSelectedParcelLocker(): void{
    
   localStorage.setItem('selectedParcelLockerId', JSON.stringify(this.selectedParcelLockerId));
  }

}
