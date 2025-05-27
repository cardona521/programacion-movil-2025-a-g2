import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CURRENT_API_URL } from './config-api';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private readonly apiUrl = `${CURRENT_API_URL}/api/facturas`;

  constructor(private readonly http: HttpClient) {
    console.log('FacturaService API URL:', this.apiUrl);
  }

  generarFactura(factura: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrar`, factura);
  }

  listarFacturas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listar`);
  }

  obtenerFacturaPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  finalizarReserva(idReserva: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/reservas/${idReserva}/estado`, { estado: 'Finalizado' });
  }

  actualizarEstadoReserva(idReserva: number, estado: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/reservas/${idReserva}/estado`, { estado });
  }
}
