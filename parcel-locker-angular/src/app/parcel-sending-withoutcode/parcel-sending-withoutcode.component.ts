import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';

@Component({
  selector: 'app-parcel-sending-withoutcode',
  templateUrl: './parcel-sending-withoutcode.component.html',
  styleUrls: ['./parcel-sending-withoutcode.component.scss']
})
export class ParcelSendingWithoutcodeComponent {

  parcelSendingForm!: FormGroup;

  parcelLockers: Array<ParcelLockerDTO> = [];

  selectedParcelLockerId: number = 0;

  favoriteSeason: string = "";
  seasons: string[] = ['Winter', 'Spring', 'Summer', 'Autumn'];
  


  constructor(private formBuilder: FormBuilder){
  }

  ngOnInit(): void{
    this.parcelSendingForm = this.formBuilder.group({
      receiverName: "",
      receiverEmailAddress: "",
      receiverPhoneNumber: "",
    });



  }

  sendForm(form: FormGroup){
    console.log();
    
    
  }

}
