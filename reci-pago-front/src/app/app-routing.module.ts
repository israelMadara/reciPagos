import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guardias/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },

  { path: 'inicio',
     loadChildren: () => import('./modulos/inicio/inicio.module').then(m => m.InicioModule),
     canActivate: [AuthGuard] },

  { path: 'autenticacion',
     loadChildren: () => import('./modulos/autenticacion/autenticacion.module').then(m => m.AutenticacionModule),
     canActivate: [AuthGuard] },


  { path: 'usuario', 
    loadChildren: () => import('./modulos/usuario/usuario.module').then(m => m.UsuarioModule),
    canActivate: [AuthGuard] },

  { path: 'servicios',
     loadChildren: () => import('./modulos/servicios/servicios.module').then(m => m.ServiciosModule),
     canActivate: [AuthGuard] },

  { path: 'pagos',
     loadChildren: () => import('./modulos/pagos/pagos.module').then(m => m.PagosModule),
     canActivate: [AuthGuard] },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
