import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LavaderoService } from '../services/lavadero.service';
import { VehiculoService } from '../services/vehiculo.service';
import { IonicModule, ToastController } from '@ionic/angular';


@Component({
  selector: 'app-registrovehiculo',
  templateUrl: './registrovehiculo.component.html',
  styleUrls: ['./registrovehiculo.component.scss'],
  standalone: false,
})
export class RegistroVehiculoComponent {
  RegisVehiculo = {
    placa: '',
    tipo: '',
    color: '',
    marca: '',
    modelo: '',
    user: {
      id: localStorage.getItem('userId')
    }
  };

  constructor(
    private lavaderoService: LavaderoService,
    private router: Router,
    private VehiculoService: VehiculoService,
    private toastController: ToastController
  ) {}

  registrarVehiculo() {
    this.VehiculoService.registrarVehiculo(this.RegisVehiculo).subscribe({
      next: async () => {
        await this.mostrarToast('Vehículo registrado correctamente', 'success');
        this.router.navigate(['/precio-servicio']);
      },
      error: async (err) => {
        await this.mostrarToast(err.error || 'No se pudo registrar el vehículo', 'danger');
      }
    });
  }

  async mostrarToast(mensaje: string, color: 'success' | 'danger' | 'warning') {
    const toast = await this.toastController.create({
      message: mensaje,
      duration: 3000,
      color,
      position: 'bottom'
    });
    await toast.present();
  }
}
