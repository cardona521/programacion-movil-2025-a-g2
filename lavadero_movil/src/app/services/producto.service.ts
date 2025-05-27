import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CURRENT_API_URL } from './config-api';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = `${CURRENT_API_URL}/api/producto`;

  constructor(private http: HttpClient) {
    console.log('ProductoService API URL:', this.apiUrl);
  }

  getProductos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  registrarProducto(producto: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/registrar`, producto);
  }

  eliminarProducto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminar/${id}`);
  }

  obtenerProductoPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}
