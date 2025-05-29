import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';


import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './guardias/jwt.interceptor';
import { FormsModule } from '@angular/forms';
import { ListaServiciosComponent } from './modulos/servicios/lista-servicios/lista-servicios.component';
import { AgregarServicioComponent } from './modulos/servicios/agregar-servicio/agregar-servicio.component';
import { Routes } from '@angular/router';

const routes: Routes = [
  { path: 'agregar-servicio', component: AgregarServicioComponent },
  { path: 'lista-servicios', component: ListaServiciosComponent },
  { path: '', redirectTo: '/agregar-servicio', pathMatch: 'full' }
];



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
   providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
