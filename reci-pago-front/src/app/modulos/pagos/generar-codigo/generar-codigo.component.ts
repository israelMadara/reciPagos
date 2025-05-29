import { Component } from '@angular/core';
import { AuthService } from 'src/app/modulos/servicios/auth.service';


@Component({
  selector: 'app-generar-codigo',
  templateUrl: './generar-codigo.component.html',
})
export class GenerarCodigoComponent {
  codigoBarras: string = '';
  servicioId: number | null = null;
  mensajeError = '';
  mensajeExito = '';

  constructor(private servicio: AuthService) {}

  generarCodigo() {
    this.mensajeError = '';
    this.mensajeExito = '';
    if (!this.servicioId) {
      this.mensajeError = 'Ingresa un ID de servicio válido';
      return;
    }

    this.servicio.generarCodigoPago(this.servicioId).subscribe({
      next: (res) => {
        this.codigoBarras = res.codigoBarras || 'Código generado';
        this.mensajeExito = 'Código generado correctamente';
      },
      error: (err) => this.mensajeError = 'Error generando código: ' + (err.error?.message || err.message)
    });
  }
}
