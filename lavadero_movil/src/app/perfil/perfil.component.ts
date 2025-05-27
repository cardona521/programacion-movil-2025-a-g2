import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss'],
  standalone: false,
})
export class PerfilComponent implements OnInit {
  usuario: any = null;

  constructor(private router: Router) {}

  ngOnInit() {
    const data = localStorage.getItem('usuarioActual');
    if (data) {
      this.usuario = JSON.parse(data);
    }
  }

  cerrarSesion() {
    localStorage.removeItem('usuarioActual');
    this.router.navigate(['/inicio-sesion']);
  }
}
