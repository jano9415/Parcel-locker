import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatTabsModule} from '@angular/material/tabs';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginMenuComponent } from './login-menu/login-menu.component';
import { CourierMenuComponent } from './courier-menu/courier-menu.component';
import { ChooseParcelLockerComponent } from './choose-parcel-locker/choose-parcel-locker.component';
import { ParcelPickingUpComponent } from './parcel-picking-up/parcel-picking-up.component';
import { ParcelSendingWithcodeComponent } from './parcel-sending-withcode/parcel-sending-withcode.component';
import { ParcelSendingWithoutcodeComponent } from './parcel-sending-withoutcode/parcel-sending-withoutcode.component';
import { CourierLoginComponent } from './courier-login/courier-login.component';
import { HttpClientModule } from '@angular/common/http';
import { NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {ReactiveFormsModule} from '@angular/forms'
import {MatRadioModule} from '@angular/material/radio';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginMenuComponent,
    CourierMenuComponent,
    ChooseParcelLockerComponent,
    ParcelPickingUpComponent,
    ParcelSendingWithcodeComponent,
    ParcelSendingWithoutcodeComponent,
    CourierLoginComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatButtonModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    HttpClientModule,
    NgFor,
    FormsModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatRadioModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
