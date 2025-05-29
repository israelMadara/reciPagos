
import { AuthService } from 'src/app/modulos/servicios/auth.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-agregar-servicio',
  templateUrl: './agregar-servicio.component.html',
})
export class AgregarServicioComponent {
  formServicio: FormGroup;
  archivoPdf: File | null = null;
  mensajeError = '';
  mensajeExito = '';

  constructor(private servicio: AuthService, private fb: FormBuilder) {
    this.formServicio = this.fb.group({
      userId: ['', Validators.required],
      contractNumber: ['', Validators.required],
      pdfFile: [null]
    });
  }

  onFileSelected(event: any) {
    this.archivoPdf = event.target.files[0];
  }

agregarServicio() {
  this.mensajeError = '';
  this.mensajeExito = '';

  if (this.formServicio.invalid || !this.archivoPdf) {
    this.mensajeError = 'Completa todos los campos y selecciona un archivo PDF.';
    return;
  }

  // Preparar FormData para enviar archivo y datos juntos
  const formData = new FormData();
  formData.append('userId', this.formServicio.value.userId);
  formData.append('contractNumber', this.formServicio.value.contractNumber);
  formData.append('pdfFile', this.archivoPdf);

  this.servicio.agregarServicio(formData).subscribe({
    next: () => this.mensajeExito = 'Servicio agregado con éxito.',
    error: err => this.mensajeError = 'Error al agregar servicio: ' + (err.error?.message || err.message)
  });
}

}
