import { Component } from '@angular/core';

@Component({
  selector: 'app-precio-servicio',
  templateUrl: './precio-servicio.component.html',
  styleUrls: ['./precio-servicio.component.scss'],
  standalone: false,
  
})
export class PrecioServicioComponent {
onImgError($event: ErrorEvent) {
throw new Error('Method not implemented.');
}
  serviciosCarros = [
    { nombre: 'Lavado Express', precio: 100000, imagen: 'Imagen1.png' },
    { nombre: 'Lavado Completo', precio: 100000, imagen: 'Imagen2.png' },
    { nombre: 'Lavado a vapor', precio: 110000, imagen: 'Imagen3.png' },
    { nombre: 'Lavado de motor', precio: 90000, imagen: 'Imagen4.png' },
    { nombre: 'Lavado interno', precio: 80000, imagen: 'Imagen5.png' },
  ];

  serviciosMotos = [
    { nombre: 'Lavado basico', precio: 50000, imagen: 'Imagen1.png' },
    { nombre: 'Lavado Cadena', precio: 20000, imagen: 'Imagen1.png' },
    { nombre: 'Engrase', precio: 30000, imagen: 'Imagen1.png' },
  ];

  serviciosBus = [
    { nombre: 'Lavado general bus', precio: 90000, imagen: 'Imagen1.png' },
    { nombre: 'Limpieza de asientos', precio: 70000, imagen: 'Imagen1.png' },
  ];
}