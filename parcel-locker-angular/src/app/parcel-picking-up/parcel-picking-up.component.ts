import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParcelService } from '../Service/parcel.service';

//Javascript függvények meghívása az assets mappából
declare function serialWrite(pickingUpCode: string): void;
declare function serialWrite2(pickingUpCode: string): void;
declare function connectToArduino(): void;
declare function printPort(): string;

@Component({
  selector: 'app-parcel-picking-up',
  templateUrl: './parcel-picking-up.component.html',
  styleUrls: ['./parcel-picking-up.component.scss']
})
export class ParcelPickingUpComponent {

  parcelPickingUpForm!: FormGroup;

  boxNumberMessage: string = "";

  paymentMessage: string = "";

  constructor(private formBuilder: FormBuilder, private parcelService: ParcelService) {

  }

  ngOnInit(): void {

    this.parcelPickingUpForm = this.formBuilder.group({
      pickingUpCode: ["", [
        Validators.required,
      ]]
    });

    connectToArduino();

  }

  proba1(): void {
    printPort();
  }

  proba2(): void {
    connectToArduino();
  }

  //Csomag átvétele
  pickUpParcel(form: FormGroup): void {


    const formValues = this.parcelPickingUpForm.value;


    const pickingUpCode = this.parcelPickingUpForm.get("pickingUpCode")?.value;

    serialWrite2(pickingUpCode);

    this.parcelService.pickUpParcel(pickingUpCode).subscribe({
      next: (response) => {
        //Csomag nem található
        if (response.message === "notFound") {
          this.boxNumberMessage = "A megadott azonosítóval nem található csomag.";

        }
        //Van ilyen csomag, de már lejárt az átvételi idő
        if(response.message === "expired"){
          this.boxNumberMessage = "A csomagod átvételi ideje lejárt. Telefonon kérheted, " + 
          "hogy a csomagot szállítsák vissza az automatába. Ez plusz költséggel jár. " +
          "Az ügyfélszolgálat telefonszáma: 0630-376-1288";
        }
        //Csomag már ki van fizetve. Át lehet venni
        if (response.message === "pickedUp") {
          this.boxNumberMessage = "Vedd ki a csomagodat a(z) " + response.boxNumber + ". rekeszből.";

        }
        //Csomagot még ki kell fizetni, utána lehet csak átvenni
        if (response.message === "notPickedUp") {
          this.paymentMessage = "Csomag még nincs kifizetve. Fizetéshez használd a bankkártya terminált.";

          //Fizetési funkció, ami majd visszatér a sikeres vagy sikertelen tranzakcióval
          const paymentState = this.payParcel(response.price);
          if (paymentState) {
            this.boxNumberMessage = "Vedd ki a csomagodat a(z) " + response.boxNumber + ". rekeszből.";

            //Csomagadatok frissítése az adatbázisban
            this.parcelService.pickUpParcelAfterPayment(pickingUpCode).subscribe({
              next: (response) => {
                console.log(response.message);
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
            this.boxNumberMessage = "Sikertelen tranzakció. Add meg újra az átvételi kódot.";
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
  get getPickingUpCode() {
    return this.parcelPickingUpForm.get("pickingUpCode");
  }



  //Példa fizetési függvény
  payParcel(price: number): boolean {

    //Sikeres tranzakció
    return(true);

    //Sikertelen tranzakció
    //return false;

  }
}
