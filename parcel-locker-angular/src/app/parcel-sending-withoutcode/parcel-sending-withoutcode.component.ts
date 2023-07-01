import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';
import { ParcelLockerService } from '../Service/parcel-locker.service';

@Component({
  selector: 'app-parcel-sending-withoutcode',
  templateUrl: './parcel-sending-withoutcode.component.html',
  styleUrls: ['./parcel-sending-withoutcode.component.scss']
})
export class ParcelSendingWithoutcodeComponent {

  parcelSendingForm!: FormGroup;

  parcelLockers: Array<ParcelLockerDTO> = [];

  selectedParcelLockerId: number = 0;

  parcelLockerFull: boolean = false;

  smallBoxesFull: boolean = false;

  mediumBoxesFull: boolean = false;

  largeBoxesFull: boolean = false;

  constructor(private formBuilder: FormBuilder, private parcelLockerService: ParcelLockerService) {
  }

  ngOnInit(): void {
    //Form bind és validáció
    this.parcelSendingForm = this.formBuilder.group({
      receiverName: ["", [
        Validators.required
      ]],
      receiverEmailAddress: ["", [
        Validators.required,
        Validators.email
      ]],
      receiverPhoneNumber: ["", [
        Validators.required
      ]],
      parcelSize: ["small", [

      ]],
      selectedParcelLockerId: [0, [
        
      ]],
      senderName: ["", [
        Validators.required
      ]],
      senderEmailAddress: ["", [
        Validators.required,
        Validators.email
      ]],
      price: [0, [
        Validators.min(0),
      ]]
    });

    //Form debug
    //this.parcelSendingForm.valueChanges.subscribe(console.log);

    //Automata tele van?

    //Kicsi rekeszek tele vannak?

    //Közepes rekeszek tele vannak?

    //Nagy rekeszek tele vannak?

    //Automaták lekérése kiválasztásra
    this.parcelLockerService.getParcelLockersForChoice().subscribe(response => {
      this.parcelLockers = response;
    })



  }

  //Csomagfeladás feladási kód nélkül
  //Form küldése
  sendForm(form: FormGroup) {
    const parcelSendingFormValues = this.parcelSendingForm.value;

    this.parcelLockerService.sendParcelWithoutCode(parcelSendingFormValues).subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        console.log("Compelete");
      }
    })

  }

  //Form változó getterek
  get getReceiverName(){
    return this.parcelSendingForm.get("receiverName");
  }

  get getPrice(){
    return this.parcelSendingForm.get("price");
  }

  get getSenderName(){
    return this.parcelSendingForm.get("senderName");
  }

  get getSenderEmailAddress(){
    return this.parcelSendingForm.get("senderEmailAddress");
  }

  get getReceiverEmailAddress(){
    return this.parcelSendingForm.get("receiverEmailAddress");
  }

  get getReceiverPhoneNumber(){
    return this.parcelSendingForm.get("receiverPhoneNumber");
  }

  get getSelectedParcelLockerId(){
    return this.parcelSendingForm.get("selectedParcelLockerId");
  }


}
