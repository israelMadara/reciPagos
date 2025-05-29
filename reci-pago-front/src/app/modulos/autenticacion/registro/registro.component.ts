import { AuthService } from 'src/app/modulos/servicios/auth.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
})
export class RegistroComponent {
  formRegistro: FormGroup;
  mensajeError = '';
  mensajeExito = '';

  constructor(private servicio: AuthService, private fb: FormBuilder) {
    this.formRegistro = this.fb.group({
      nombre: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      contrasena: ['', Validators.required],
    });
  }

  registrar() {
    this.mensajeError = '';
    this.mensajeExito = '';
    if (this.formRegistro.invalid) return;

    this.servicio.registrar(this.formRegistro.value).subscribe({
      next: () => this.mensajeExito = 'Registro exitoso, verifica tu OTP en WhatsApp.',
      error: err => this.mensajeError = 'Error al registrar: ' + err.error?.message || err.message
    });
  }
}
