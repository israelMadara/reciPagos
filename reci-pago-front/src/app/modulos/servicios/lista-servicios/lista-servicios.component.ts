import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modulos/servicios/auth.service';
// lista-servicios.component.ts

@Component({
  selector: 'app-lista-servicios',
  templateUrl: './lista-servicios.component.html',
})
export class ListaServiciosComponent implements OnInit {
  servicios: any[] = [];
  usuarioId = 1; // TODO: obtener usuario actual (ejemplo hardcoded)

  constructor(private servicio: AuthService) {}

  ngOnInit() {
    this.cargarServicios();
  }

  cargarServicios() {
    this.servicio.obtenerServicios(this.usuarioId).subscribe({
      next: (res) => this.servicios = res,
      error: (err) => console.error('Error cargando servicios', err)
    });
  }
}

