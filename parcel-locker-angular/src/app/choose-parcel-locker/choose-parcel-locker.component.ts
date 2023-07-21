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

  selectedParcelLocker: ParcelLockerDTO | null = null;

  constructor(private parcelLockerService: ParcelLockerService) {

  }

  ngOnInit(): void {
    //Automaták lekérése kiválasztásra
    this.parcelLockerService.getParcelLockersForChoice().subscribe({
      next: (response) => {
        this.parcelLockers = response;
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        console.log("Compelete");
      }
    })

  }

  //Kiválasztott automata mentése local storage-ra
  sendSelectedParcelLocker(): void {

    localStorage.setItem('senderParcelLockerId', JSON.stringify(this.selectedParcelLocker?.id));

    localStorage.setItem('senderParcelLockerPostCode', JSON.stringify(this.selectedParcelLocker?.postCode));

    localStorage.setItem('senderParcelLockerCity', JSON.stringify(this.selectedParcelLocker?.city));

    localStorage.setItem('senderParcelLockerStreet', JSON.stringify(this.selectedParcelLocker?.street));

  }

}
