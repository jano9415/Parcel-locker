import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ParcelSendingWithcodeComponent } from './parcel-sending-withcode/parcel-sending-withcode.component';
import { ParcelSendingWithoutcodeComponent } from './parcel-sending-withoutcode/parcel-sending-withoutcode.component';
import { ParcelPickingUpComponent } from './parcel-picking-up/parcel-picking-up.component';
import { CourierLoginComponent } from './courier-login/courier-login.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'parcelsendingwithcode', component: ParcelSendingWithcodeComponent},
  {path: 'parcelsendingwithoutcode', component: ParcelSendingWithoutcodeComponent},
  {path: 'parcelpickingup', component: ParcelPickingUpComponent},
  {path: 'courierlogin', component: CourierLoginComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
