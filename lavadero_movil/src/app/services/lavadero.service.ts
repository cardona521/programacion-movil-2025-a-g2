import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CURRENT_API_URL } from './config-api';

@Injectable({
  providedIn: 'root'
})
export class LavaderoService {



  private apiUrl = `${CURRENT_API_URL}/api/users`;

  constructor(private http: HttpClient) {
    console.log('API URL:', this.apiUrl);
  }

  registrarUsuario(usuario: any): Observable<any> {
    const fullUrl = `${this.apiUrl}/register`;
    console.log('Full registration URL:', fullUrl);
    return this.http.post<any>(fullUrl, usuario);
  }

  login(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, usuario);
  }

  recuperarContrasena(datos: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/recuperar`, datos, { responseType: 'text' });
  }

  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  actualizarUsuario(id: number, usuario: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, usuario);
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  registrarVehiculo(vehiculo: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registroVehiculo`, vehiculo);
  }
}
