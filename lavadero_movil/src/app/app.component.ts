import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router'; // ✅ Import Router and RouterModule

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  standalone: false, // ✅ Asegúrate de que sea 'true' si es un componente independiente
  providers: [], // Optionally add providers if needed
})
export class AppComponent {
  rol: string | null = null;

  constructor(private router: Router) {
    this.loadUserrol();
  }

  loadUserrol(): void {
    const rol = localStorage.getItem('rol');
    this.rol = rol ? rol : null;
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/inicio-sesion']);
  }
}
