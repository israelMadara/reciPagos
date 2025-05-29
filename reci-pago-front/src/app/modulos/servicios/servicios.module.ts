import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServiciosRoutingModule } from './servicios-routing.module';
import { ServiciosComponent } from './servicios.component';
import { AgregarServicioComponent } from './agregar-servicio/agregar-servicio.component';
import { ListaServiciosComponent } from './lista-servicios/lista-servicios.component';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


const routes: Routes = [
  { path: 'agregar', component: AgregarServicioComponent },
  { path: '', component: ListaServiciosComponent }
];

@NgModule({
  declarations: [
    ServiciosComponent,
    AgregarServicioComponent,
    ListaServiciosComponent
  ],
  
   imports: [CommonModule, RouterModule.forChild(routes), MaterialModule, FormsModule, ReactiveFormsModule,ServiciosRoutingModule]
  
})
export class ServiciosModule { }
