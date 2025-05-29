import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:1994/api/recipago';

  constructor(private http: HttpClient) {}

  registrarUsuario(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, data);
  }

  login(data: any): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, data);
  }

  validarOtp(telefono: string, otp: string): Observable<boolean> {
    const params = new HttpParams()
      .set('telefono', telefono)
      .set('otp', otp);
    return this.http.post<boolean>(`${this.apiUrl}/validar-otp`, null, { params });
  }

  obtenerUsuarioActual(): Observable<any> {
    return this.http.get(`${this.apiUrl}/usuario/actual`);
  }




agregarServicio(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/servicios`, formData);
  }

  obtenerServicios(usuarioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/servicios/${usuarioId}`);
  }

  generarCodigoOxxo(servicioId: number): Observable<any> {
    // Debes tener este endpoint implementado en tu backend
    return this.http.post<any>(`${this.apiUrl}/pagos`, {
      servicioId,
      codigoBarras: 'GENERADO-123456', // reemplaza por lógica real
      montoPagado: 500,
      comision: 10
    });
  }

  obtenerPagos(servicioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pagos/${servicioId}`);
  }



   registrar(usuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, usuario);
  }


  generarCodigoPago(servicioId: number): Observable<any> {
    // Asumir ruta para generar código (puedes ajustar según backend)
    return this.http.post(`${this.apiUrl}/pagos`, { servicioId });
  }

  obtenerHistorialPagos(servicioId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/pagos/${servicioId}`);
  }


  estaAutenticado(): boolean {
  return !!localStorage.getItem('token');
}



}
