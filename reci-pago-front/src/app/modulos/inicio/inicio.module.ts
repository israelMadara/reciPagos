import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InicioRoutingModule } from './inicio-routing.module';
import { InicioComponent } from './inicio.component';
import { PaginaInicioComponent } from './pagina-inicio/pagina-inicio.component';


@NgModule({
  declarations: [
    InicioComponent,
    PaginaInicioComponent
  ],
  imports: [
    CommonModule,
    InicioRoutingModule
  ]
})
export class InicioModule { }
