import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagosRoutingModule } from './pagos-routing.module';
import { PagosComponent } from './pagos.component';
import { GenerarCodigoComponent } from './generar-codigo/generar-codigo.component';
import { HistorialPagosComponent } from './historial-pagos/historial-pagos.component';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule } from '@angular/forms';


const routes: Routes = [
  { path: 'generar', component: GenerarCodigoComponent },
  { path: 'historial', component: HistorialPagosComponent }
];

@NgModule({
  declarations: [
    PagosComponent,
    GenerarCodigoComponent,
    HistorialPagosComponent
  ],
  imports: [
    CommonModule,
    PagosRoutingModule,
    RouterModule.forChild(routes), MaterialModule, FormsModule
  ]
})
export class PagosModule { }
