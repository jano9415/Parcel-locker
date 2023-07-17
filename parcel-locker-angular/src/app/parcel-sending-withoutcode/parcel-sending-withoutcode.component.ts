import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParcelLockerDTO } from '../Payload/parcel-locker-dto';
import { ParcelLockerService } from '../Service/parcel-locker.service';
import { ParcelService } from '../Service/parcel.service';
import { Router } from '@angular/router';

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

  boxNumberMessage: string = "";

  paymentMessage: string = "";

  constructor(private formBuilder: FormBuilder, private parcelLockerService: ParcelLockerService,
    private parcelService: ParcelService, private router: Router) {
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
    this.parcelLockerService.isParcelLockerFull().subscribe({
      next: (response) => {
        if (response.message === "full") {
          this.parcelLockerFull = true;
        }
        if (response.message === "notfull") {
          this.parcelLockerFull = false;
        }
      },
      error: (error) => {
        console.log(error);
      },
      complete: () => {
        console.log("Compelete");
      }
    })

    //Ha az automata nincs tele, akkor ellenőrzöm a kis, közepes és nagy rekeszek telítettségét.
    //Automaták lekérése kiválasztásra
    if (this.parcelLockerFull == false) {

      this.parcelLockerService.areBoxesFull().subscribe({
        next: (response) => {
          //Kicsi rekeszek
          if (response[0].message === "full") {
            this.smallBoxesFull = true;
          }
          if (response[0].message === "notfull") {
            this.smallBoxesFull = false;
          }
          //Közepes rekeszek
          if (response[1].message === "full") {
            this.mediumBoxesFull = true;
          }
          if (response[1].message === "notfull") {
            this.mediumBoxesFull = false;
          }
          //Nagy rekeszek
          if (response[2].message === "full") {
            this.largeBoxesFull = true;
          }
          if (response[2].message === "notfull") {
            this.largeBoxesFull = false;
          }
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          console.log("Compelete");
        }
      })

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
  }

  //Kicsi, közepes vagy nagy rekeszek telítettségének ellenőrzése
  checkBoxesInSelectedSize(size: string): boolean {

    if (size === "small") {
      return this.smallBoxesFull
    }
    if (size === "small") {
      return this.mediumBoxesFull
    }
    return this.largeBoxesFull

  }

  //Csomagfeladás feladási kód nélkül
  //Form küldése
  sendForm(form: FormGroup) {
    const parcelSendingFormValues = this.parcelSendingForm.value;

    //Miután az ügyfél megnyomja a csomagfeladás gombot, újra ellenőrzöm az automata telítettségét
    //Lehet, hogy az automata a form kitöltése közben megtelt, az online csomagküldés lehetősége miatt
    //Ekkor nem is indítok fizetési tranzakciót
    const selectedParcelSize = this.parcelSendingForm.get("parcelSize")?.value;

    this.parcelLockerService.areBoxesFull().subscribe({
      next: (response) => {
        //Kicsi rekeszek
        if (response[0].message === "full") {
          this.smallBoxesFull = true;
        }
        if (response[0].message === "notfull") {
          this.smallBoxesFull = false;
        }
        //Közepes rekeszek
        if (response[1].message === "full") {
          this.mediumBoxesFull = true;
        }
        if (response[1].message === "notfull") {
          this.mediumBoxesFull = false;
        }
        //Nagy rekeszek
        if (response[2].message === "full") {
          this.largeBoxesFull = true;
        }
        if (response[2].message === "notfull") {
          this.largeBoxesFull = false;
        }



        if (this.checkBoxesInSelectedSize(selectedParcelSize)) {
          this.boxNumberMessage = "Sajnos a kiválasztott méretű rekeszek megteltek online csomagfeladás miatt.";
          this.parcelSendingForm.reset();
        }
        else {
          //Csomagfeladás kifizetése
          this.paymentMessage = "Fizetéshez használja a bankkártya terminált.";
          const paymentState = this.payParcel(4600);
          if (paymentState) {

            //Kérés a szervernek
            this.parcelService.sendParcelWithoutCode(parcelSendingFormValues).subscribe({
              next: (response) => {
                //Sikeres csomagfeladás
                if (response.message === "successSending") {
                  this.boxNumberMessage = "Tedd be a csomagodat a(z) " + response.boxNumber + ". rekeszbe.";
                  this.parcelSendingForm.reset();
                }

              },
              error: (error) => {
                console.log(error);
              },
              complete: () => {
                console.log("Compelete");
              }
            })

          }
          else {
            this.boxNumberMessage = "Sikertelen tranzakció. Kérjük próbálja meg újra feladni a csomagját.";

          }

        }
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
  get getReceiverName() {
    return this.parcelSendingForm.get("receiverName");
  }

  get getPrice() {
    return this.parcelSendingForm.get("price");
  }

  get getParcelSize() {
    return this.parcelSendingForm.get("parcelSize");
  }

  get getSenderName() {
    return this.parcelSendingForm.get("senderName");
  }

  get getSenderEmailAddress() {
    return this.parcelSendingForm.get("senderEmailAddress");
  }

  get getReceiverEmailAddress() {
    return this.parcelSendingForm.get("receiverEmailAddress");
  }

  get getReceiverPhoneNumber() {
    return this.parcelSendingForm.get("receiverPhoneNumber");
  }

  get getSelectedParcelLockerId() {
    return this.parcelSendingForm.get("selectedParcelLockerId");
  }


  //Példa fizetési függvény
  payParcel(price: number): boolean {

    console.log('Tranzakció folyamatban.');

    //Sikeres tranzakció
    return(true);

    //Sikertelen tranzakció
    //return false;

  }

}
