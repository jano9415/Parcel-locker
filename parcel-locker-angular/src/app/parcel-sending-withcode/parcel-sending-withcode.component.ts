import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParcelService } from '../Service/parcel.service';

@Component({
  selector: 'app-parcel-sending-withcode',
  templateUrl: './parcel-sending-withcode.component.html',
  styleUrls: ['./parcel-sending-withcode.component.scss']
})
export class ParcelSendingWithcodeComponent {

  parcelsendingWithCodeForm!: FormGroup;

  boxNumberMessage: string = "";

  paymentMessage: string = "";

  constructor(private formBuilder: FormBuilder, private parcelService: ParcelService) {

  }

  ngOnInit(): void {

    this.parcelsendingWithCodeForm = this.formBuilder.group({
      sendingCode: ["", [
        Validators.required,
      ]]
    });

  }

  //Csomag küldése
  sendParcel(form: FormGroup): void {

  }

  //Form változó getterek
  get getSendingCode() {
    return this.parcelsendingWithCodeForm.get("sendingCode");
  }

}
