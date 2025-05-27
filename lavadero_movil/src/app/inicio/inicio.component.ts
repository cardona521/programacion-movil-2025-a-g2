import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss'],
  standalone: false,
})
export class InicioComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}
  // En el archivo .ts correspondiente (por ejemplo, home.page.ts)

servicios = [
  { nombre: 'Lavado Express', img: 'assets/Imagen1.png' },
  { nombre: 'Lavado Completo', img: 'assets/Imagen2.png' },
  { nombre: 'Lavado a vapor', img: 'assets/Imagen3.png' },
  { nombre: 'Lavado de motor', img: 'assets/Imagen4.png' },
  { nombre: 'Lavado interno', img: 'assets/Imagen5.png' },
];


}
