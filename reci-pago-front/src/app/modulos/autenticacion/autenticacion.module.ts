import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AutenticacionRoutingModule } from './autenticacion-routing.module';
import { AutenticacionComponent } from './autenticacion.component';
import { RegistroComponent } from './registro/registro.component';
import { OtpComponent } from './otp/otp.component';
import { LoginComponent } from './login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/material.module';


@NgModule({
declarations: [
  RegistroComponent,
  LoginComponent,
  OtpComponent
],
imports: [
  CommonModule,
  ReactiveFormsModule,
  MaterialModule,
  AutenticacionRoutingModule
]

})
export class AutenticacionModule { }
