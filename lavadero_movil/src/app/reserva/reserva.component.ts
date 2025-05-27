import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LavaderoService } from '../services/lavadero.service';
import { ReservaService } from '../services/reserva.service';
import { VehiculoService } from '../services/vehiculo.service';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-reserva',
  templateUrl: './reserva.component.html',
  styleUrls: ['./reserva.component.scss'],
  standalone: false,
})
export class ReservaComponent implements OnInit {
  vehiculos: any[] = [];
  tiposServicio: string[] = [];
  reservaSeleccionada: any = null;
  metodoPago: string = '';
  mostrarFactura: boolean = false;

  RegisVehiculo = {
    vehiculo: {
      id: null,
      modelo: '',
    },
    tipo: '',
    fecha: ''
  };
serviciosBus: any;

  constructor(
    private reservaService: ReservaService,
    private lavaderoService: LavaderoService,
    private vehiculoService: VehiculoService,
    private router: Router,
    private alertController: AlertController
  ) {}

  ngOnInit(): void {
    this.cargarVehiculosUsuario();
  }

  async presentAlert(header: string, message: string, type: 'success' | 'error' | 'warning') {
    const alert = await this.alertController.create({
      header,
      message,
      buttons: ['OK']
    });
    await alert.present();
  }

  cargarVehiculosUsuario(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.vehiculoService.getVehiculosPorUsuario(userId).subscribe({
        next: (data) => {
          this.vehiculos = data;
        },
        error: () => {
          this.presentAlert('Error', 'No se pudieron cargar los vehículos', 'error');
        },
      });
    } else {
      this.presentAlert('Error', 'No se encontró el ID del usuario', 'error');
    }
  }

  crearreserva(): void {
    const userId = localStorage.getItem('userId');
    if (!userId || !this.RegisVehiculo.vehiculo.id || !this.RegisVehiculo.tipo || !this.RegisVehiculo.fecha) {
      this.presentAlert('Campos incompletos', 'Debe completar todos los campos: vehículo, servicio y fecha', 'warning');
      return;
    }

    const reserva = {
      estado: 'pendiente',
      fechaReserva: this.RegisVehiculo.fecha,
      servicio: this.RegisVehiculo.tipo,
      user: { id: parseInt(userId) },
      vehiculo: { id: this.RegisVehiculo.vehiculo.id },
    };

    this.reservaService.crearReserva(reserva).subscribe({
      next: (data) => {
        this.presentAlert('Éxito', 'Reserva registrada correctamente', 'success');
        this.reservaSeleccionada = data;
      },
      error: (err) => {
        this.presentAlert('Error', err.error || 'No se pudo registrar la reserva', 'error');
      }
    });
  }

  onVehiculoSeleccionado(idVehiculo: number) {
    const vehiculo = this.vehiculos.find(v => v.id === +idVehiculo);
    if (vehiculo) {
      this.RegisVehiculo.vehiculo = vehiculo;
      this.cargarTiposServicioPorVehiculo(vehiculo.tipo);
    }
  }

  cargarTiposServicioPorVehiculo(tipoVehiculo: string) {
    this.reservaService.getTiposServicio(tipoVehiculo).subscribe({
      next: (data) => this.tiposServicio = data,
      error: (error) => console.error('Error al cargar tipos de servicio:', error)
    });
  }

  mostrarResumenPago(metodo: string): void {
    if (!this.reservaSeleccionada) return;

    this.metodoPago = metodo;

    this.reservaService.actualizarMetodoPago(this.reservaSeleccionada.id, metodo).subscribe({
      next: () => {
        this.mostrarFactura = true;
      },
      error: (err) => {
        console.error('Error al guardar el método de pago:', err);
        this.presentAlert('Error', 'No se pudo guardar el método de pago', 'error');
      }
    });
  }

  nuevaReserva(): void {
    this.RegisVehiculo = {
      vehiculo: { id: null, modelo: '' },
      tipo: '',
      fecha: ''
    };
    this.reservaSeleccionada = null;
    this.metodoPago = '';
    this.mostrarFactura = false;
  }
}
