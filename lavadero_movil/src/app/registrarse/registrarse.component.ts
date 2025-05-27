import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LavaderoService } from '../services/lavadero.service';

@Component({
  selector: 'app-registrarse',
  templateUrl: './registrarse.component.html',
  styleUrl: './registrarse.component.scss',
  standalone: false,
})
export class RegisterComponent {
  usuario = {
    nombre: '',
    correo: '',
    password: '',
    celular: '',
    tipoCedula: 'CC',
    cedula: '',
    rol: ''
  };

  alerta: { titulo: string; mensaje: string } | null = null;

  constructor(private lavaderoService: LavaderoService, private router: Router) {}

  async register() {
    // Validación de campos
    if (
      !this.usuario.nombre.trim() ||
      !this.usuario.correo.trim() ||
      !this.usuario.password.trim() ||
      !this.usuario.celular.trim() ||
      !this.usuario.cedula.trim() ||
      !this.usuario.tipoCedula.trim() ||
      !this.usuario.rol.trim()
    ) {
      this.mostrarAlerta('Campos incompletos', 'Por favor, complete todos los campos antes de registrarse.');
      return;
    }

    // Normalizar datos
    this.usuario.celular = this.usuario.celular.toString();
    this.usuario.cedula = this.usuario.cedula.toString();

    // DEBUG: Log the usuario object and environment
    console.log('Environment check:', import('src/environments/environment'));
    console.log('Usuario to register:', this.usuario);
    
    try {
      // Enviar registro con debugging
      this.lavaderoService.registrarUsuario(this.usuario).subscribe({
        next: (response) => {
          console.log('Registration successful:', response);
          this.mostrarAlerta('¡Registro exitoso!', 'Tu cuenta ha sido creada correctamente.');
          setTimeout(() => this.router.navigate(['/inicio-sesion']), 1500);
        },
        error: (err) => {
          console.error('Registration error:', err);
          let errorMessage = 'No se pudo completar el registro. Intenta de nuevo.';

          if (err.status === 400) {
            if (err.error.includes('correo')) {
              errorMessage = 'El correo ya está en uso.';
            } else if (err.error.includes('celular')) {
              errorMessage = 'El número de celular ya está en uso.';
            } else if (err.error.includes('cédula y tipo de cédula')) {
              errorMessage = 'Ya existe un usuario con esta cédula y este tipo de cédula.';
            } else {
              errorMessage = err.error;
            }
          } else if (err.status === 500) {
            errorMessage = 'Error en el servidor. Intente más tarde.';
          }

          this.mostrarAlerta('Error', errorMessage);
        }
      });
    } catch (error) {
      console.error('Subscription error:', error);
      this.mostrarAlerta('Error', 'Error al intentar registrar usuario.');
    }
  }

  mostrarAlerta(titulo: string, mensaje: string) {
    this.alerta = { titulo, mensaje };
  }

  onAlertConfirm() {
    this.alerta = null;
  }
}