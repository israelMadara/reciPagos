
import { AuthService } from 'src/app/modulos/servicios/auth.service';
// login.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  formLogin: FormGroup;
  mensajeError = '';

  constructor(
    private servicio: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.formLogin = this.fb.group({
      telefono: ['', Validators.required],
      contrasena: ['', Validators.required],
    });
  }

  login() {
    this.mensajeError = '';
    if (this.formLogin.invalid) return;

    this.servicio.login(this.formLogin.value).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => (this.mensajeError = 'Teléfono o contraseña incorrectos'),
    });
  }
}
