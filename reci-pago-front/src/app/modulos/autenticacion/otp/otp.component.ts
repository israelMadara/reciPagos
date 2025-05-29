
import { AuthService } from 'src/app/modulos/servicios/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
// otp.component.ts
import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
})
export class OtpComponent {
  @Input() telefono: string = '';
  formOtp: FormGroup;
  mensajeError = '';
  mensajeExito = '';

  constructor(private servicio: AuthService, private fb: FormBuilder, private router: Router) {
    this.formOtp = this.fb.group({
      otp: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(6)]],
    });
  }

  validarOtp() {
    this.mensajeError = '';
    this.mensajeExito = '';

    if (this.formOtp.invalid) return;

    this.servicio.validarOtp(this.telefono, this.formOtp.value.otp).subscribe({
      next: (valido) => {
        if (valido) {
          this.mensajeExito = 'Cuenta activada correctamente.';
          setTimeout(() => this.router.navigate(['/login']), 1500);
        } else {
          this.mensajeError = 'OTP inválido o expirado.';
        }
      },
      error: (err) => (this.mensajeError = 'Error validando OTP'),
    });
  }
}
