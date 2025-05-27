import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CURRENT_API_URL } from './config-api';

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {
  private apiUrl = `${CURRENT_API_URL}/api/vehiculos`;

  constructor(private http: HttpClient) {
    console.log('VehiculoService API URL:', this.apiUrl);
  }

  registrarVehiculo(vehiculo: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrar`, vehiculo);
  }

  getVehiculosPorUsuario(userId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/u/${userId}`);
  }
}
