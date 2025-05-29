import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modulos/servicios/auth.service';

@Component({
  selector: 'app-historial-pagos',
  templateUrl: './historial-pagos.component.html',
})
export class HistorialPagosComponent {
  servicioId: number | null = null;
  pagos: any[] = [];
  mensajeError = '';

  constructor(private servicio: AuthService) {}

  obtenerHistorial() {
    this.mensajeError = '';
    this.pagos = [];

    if (!this.servicioId) {
      this.mensajeError = 'Ingresa un ID de servicio válido';
      return;
    }

    this.servicio.obtenerHistorialPagos(this.servicioId).subscribe({
      next: (res) => this.pagos = res,
      error: (err) => this.mensajeError = 'Error al obtener historial: ' + (err.error?.message || err.message)
    });
  }
}
