import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CURRENT_API_URL } from './config-api';

@Injectable({
  providedIn: 'root'
})
export class HistorialService {

  private apiUrl = `${CURRENT_API_URL}/api/reservas`;

  constructor(private http: HttpClient) {
    console.log('HistorialService API URL:', this.apiUrl);
  }

  getReservas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getReservasPorUsuario(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/usuario/${userId}`);
  }

  getReservaById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  deleteReserva(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  crearReserva(reserva: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, reserva);
  }

  actualizarEstadoReserva(id: number, estado: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/estado`, { estado });
  }
}
