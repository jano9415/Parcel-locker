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

  //Csomagfeladás feladási kóddal
  //Ha a csomag nem található a válasz: message: notFound
  //Ha a csomag megtalálható a válasz: message: found, boxNumber: rekesz száma
  //Ha megvan a csomag, először ki kell fizetni a szállítási díjat
  //Utána kinyílik a rekesz, és megtörténik az adatbázis frissítése
  sendParcel(form: FormGroup): void {

    this.parcelService.getParcelForSendingWithCode(this.getSendingCode?.value).subscribe({
      next: (response) => {
        //Ha van csomag, akkor még ki kell fizetni a feladást
        if(response.message === "found"){

          this.paymentMessage = "Fizetéshez használd a bankkártya terminált.";
          const paymentState = this.payParcel(4600);

          if(paymentState){
            //Adatok frissítése az adatbázisban
            this.parcelService.sendParcelWithCode(this.getSendingCode?.value).subscribe({
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

            this.boxNumberMessage = "Tedd be a csomagodat a(z) " + response.boxNumber + ". rekeszbe.";
            this.parcelsendingWithCodeForm.reset();

          }
          else{
            this.boxNumberMessage = "Sikertelen tranzakció. Próbáld meg újra feladni a csomagot.";
          }
        }
        //Csomag nem található
        if(response.message === "notFound"){
          this.boxNumberMessage = "A megadott feladási kóddal nem található csomag.";

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
  get getSendingCode() {
    return this.parcelsendingWithCodeForm.get("sendingCode");
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
