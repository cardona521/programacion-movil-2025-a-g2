# HU-05: Inicio de sesión

**Como** usuario registrado  
**Quiero** poder iniciar sesión con mi correo y contraseña  
**Para** acceder a las funcionalidades personalizadas del sistema.

###  Criterios de Aceptación:

- El formulario solicita correo electrónico y contraseña.
- El sistema valida las credenciales contra la base de datos.
- El sistema diferencia entre usuarios normales y administradores mostrando diferentes opciones.
- Existe un enlace para recuperación de contraseña.
- Se implementan medidas de seguridad contra intentos de acceso no autorizados.

---

###  Definición de Listo (DoR - Definition of Ready):

- Se ha definido el proceso de autenticación de usuarios.
- Se han establecido los niveles de acceso según el rol del usuario.
- Se ha diseñado el formulario de inicio de sesión.
- Se ha definido el manejo de sesiones en el sistema.

---

###  Definición de Hecho (DoD - Definition of Done):

- El formulario de inicio de sesión funciona correctamente.
- El sistema reconoce los diferentes roles y muestra las opciones correspondientes.
- Las sesiones se manejan de forma segura.
- Las pruebas de seguridad han sido ejecutadas exitosamente.
- La experiencia de usuario es fluida y sin errores.

---

###  Prioridad y Estimación:

- **Prioridad:** Alta
- **Estimación:** 3 días

### codigo para realizar el inicio de sesion

import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { LavaderoService } from '../services/lavadero.service';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-inicio-sesion',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './inicio-sesion.component.html',
  styleUrl: './inicio-sesion.component.css'
})
export class InicioSesionComponent {
  usuario = {
    correo: '',
    password: ''
  };

  constructor(private lavaderoService: LavaderoService, private router: Router) {}

  iniciarSesion() {

    this.lavaderoService.login(this.usuario).subscribe({
      next: (response: any) => {
        response = JSON.parse(response); // Asegúrate de que la respuesta sea un objeto

        localStorage.setItem('userId', response.userId);
        localStorage.setItem('rol', response.rol); // <-- Guardamos el rol

        Swal.fire({
          title: 'Bienvenido',
          text: response.mensaje, //  Muestra la respuesta del backend
          icon: 'success',
          confirmButtonText: 'Continuar'
        }).then(() => {
          this.router.navigate(['/precio-servicio']); // NO CAMBIADO
        });
      },
      error: (err) => {
        Swal.fire({
          title: 'Error',
          text: 'Correo o contraseña incorrectos.',
          icon: 'error',
          confirmButtonText: 'Intentar de nuevo'
        });
      }
    });
  }

  // Método para abrir la alerta de recuperación de contraseña
  abrirRecuperacion() {
    Swal.fire({
      title: 'Recuperar Contraseña',
      input: 'email',
      inputLabel: 'Ingresa tu correo electrónico',
      inputPlaceholder: 'correo@example.com',
      showCancelButton: true,
      confirmButtonText: 'Recuperar',
      preConfirm: (correo) => {
        if (!correo) {
          Swal.showValidationMessage('El correo es obligatorio');
          return false;
        }
        return correo;
      }
    }).then((result) => {
      if (result.isConfirmed) {
        this.lavaderoService.recuperarContrasena({ correo: result.value }).subscribe({
          next: (response: string) => {
            Swal.fire('Éxito', response, 'success');
          },
          error: (err) => {
            console.error('Error en recuperación:', err);

            let mensajeError = 'No se pudo recuperar la contraseña. Inténtalo nuevamente.';
            if (err.status === 404) {
              mensajeError = 'El correo ingresado no está registrado.';
            } else if (err.status === 500) {
              mensajeError = 'Hubo un error en el servidor. Inténtalo más tarde.';
            }

            Swal.fire({
              title: 'Error',
              text: mensajeError,
              icon: 'error',
              confirmButtonText: 'Intentar de nuevo'
            });
          }
        });
      }
    });
  }

  // Método para enviar la solicitud de recuperación de contraseña al backend
  recuperarContrasena(correo: string) {
    this.lavaderoService.recuperarContrasena({ correo }).subscribe({
      next: () => {
        Swal.fire({
          title: 'Éxito',
          text: 'Se ha enviado una nueva contraseña a tu correo.',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        });
      },
      error: () => {
        Swal.fire({
          title: 'Error',
          text: 'No se pudo recuperar la contraseña. Verifica el correo ingresado.',
          icon: 'error',
          confirmButtonText: 'Intentar de nuevo'
        });
      }
    });
  }
}
