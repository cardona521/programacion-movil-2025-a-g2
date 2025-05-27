import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LavaderoService } from '../services/lavadero.service';
import { ToastController, AlertController } from '@ionic/angular';

@Component({
  selector: 'app-inicio-sesion',
  templateUrl: './inicio-sesion.component.html',
  styleUrls: ['./inicio-sesion.component.scss'],
  standalone: false,
})
export class InicioSesionComponent {
  usuario = {
    correo: '',
    password: ''
  };

  constructor(
    private lavaderoService: LavaderoService,
    private router: Router,
    private toastController: ToastController,
    private alertController: AlertController
  ) {}

  async mostrarToast(mensaje: string, color: string = 'dark') {
    const toast = await this.toastController.create({
      message: mensaje,
      duration: 3000,
      color,
      position: 'top',
      buttons: [{ text: 'Cerrar', role: 'cancel' }]
    });
    await toast.present();
  }

  iniciarSesion() {
    this.lavaderoService.login(this.usuario).subscribe({
     next: async (response: any) => {
  console.log('Respuesta login:', response);

  
  localStorage.setItem('userId', response.userId);
  localStorage.setItem('rol', response.rol);


        await this.mostrarToast(response.mensaje, 'success');

        // Redirección según rol
        switch (response.rol) {
          case 'admin':
            this.router.navigate(['/gestionventas']);  
            break;
          case 'cliente':
            this.router.navigate(['/precio-servicio']);
            break;
          default:
            this.router.navigate(['/precio-servicio']);
            break;
        }
      },
      error: async () => {
        await this.mostrarToast('Correo o contraseña incorrectos.', 'danger');
      }
    });
  }

  async abrirRecuperacion() {
    const alert = await this.alertController.create({
      header: 'Recuperar Contraseña',
      inputs: [
        {
          name: 'correo',
          type: 'email',
          placeholder: 'correo@example.com'
        }
      ],
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel'
        },
        {
          text: 'Recuperar',
          handler: (data) => {
            if (!data.correo) {
              this.mostrarToast('El correo es obligatorio.', 'warning');
              return false;
            }

            this.lavaderoService.recuperarContrasena({ correo: data.correo }).subscribe({
              next: async (res: string) => {
                await this.mostrarToast(res, 'success');
              },
              error: async (err) => {
                let mensajeError = 'No se pudo recuperar la contraseña.';
                if (err.status === 404) mensajeError = 'El correo no está registrado.';
                if (err.status === 500) mensajeError = 'Error en el servidor.';
                await this.mostrarToast(mensajeError, 'danger');
              }
            });

            return true;
          }
        }
      ]
    });

    await alert.present();
  }
}
