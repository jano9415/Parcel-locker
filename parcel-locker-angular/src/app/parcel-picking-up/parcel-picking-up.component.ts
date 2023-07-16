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

  boxNumberMessage: string = "";

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
        //Csomag nem található
        if (response.message === "notFound") {
          this.boxNumberMessage = "A megadott azonosítóval nem található csomag.";

        }
        //Csomag már ki van fizetve. Át lehet venni
        if (response.message === "pickedUp") {
          this.boxNumberMessage = "Vedd ki a csomagodat a(z) " + response.boxNumber + ". rekeszből.";

        }
        //Csomagot még ki kell fizetni, utána lehet csak átvenni
        if (response.message === "notPickedUp") {
          this.boxNumberMessage = "Csomag még nincs kifizetve. Az átvétel befejezéséhez használja a bankkártya terminált.";

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
            this.boxNumberMessage = "Sikertelen tranzakció. Új tranzakció végrehajtásához adja meg újra az átvételi kódot.";
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
    //return(true);

    //Sikertelen tranzakció
    return false;

  }
}
