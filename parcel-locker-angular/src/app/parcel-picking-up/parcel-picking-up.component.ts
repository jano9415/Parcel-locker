import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParcelService } from '../Service/parcel.service';

@Component({
  selector: 'app-parcel-picking-up',
  templateUrl: './parcel-picking-up.component.html',
  styleUrls: ['./parcel-picking-up.component.scss']
})
export class ParcelPickingUpComponent {

  parcelPickingUpForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private parcelService: ParcelService) {

  }

  ngOnInit(): void {

    this.parcelPickingUpForm = this.formBuilder.group({
      pickingUpCode: ["", [
        Validators.required,
      ]]
    });

  }

  //Csomag átvétele
  pickUpParcel(form: FormGroup): void {

    const formValues = this.parcelPickingUpForm.value;

    
    const pickingUpCode = this.parcelPickingUpForm.get("pickingUpCode")?.value;

    this.parcelService.pickUpParcel(pickingUpCode).subscribe({
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
  get getPickingUpCode() {
    return this.parcelPickingUpForm.get("pickingUpCode");
  }



}
