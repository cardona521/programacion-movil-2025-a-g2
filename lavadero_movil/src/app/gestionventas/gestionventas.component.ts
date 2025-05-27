import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

import Swal from 'sweetalert2';

import { VehiculoService } from '../services/vehiculo.service';
import { ReservaService } from '../services/reserva.service';
import { LavaderoService } from '../services/lavadero.service';

@Component({
  selector: 'app-gestionventas',
  templateUrl: './gestionventas.component.html',
  styleUrls: ['./gestionventas.component.scss'],
  standalone: false,
})
export class GestionVentasComponent {
  formulario: FormGroup;
  tiposVehiculo = ['Carro', 'Moto', 'Bus'];
  tiposServicio: string[] = [];
  mensaje: string = '';
  mostrarFactura: boolean = false;
  reservaSeleccionada: any = null;
  metodoPago: string = '';

  constructor(
    private fb: FormBuilder,
    private vehiculoService: VehiculoService,
    private lavaderoService: LavaderoService,
    private reservaService: ReservaService
  ) {
    this.formulario = this.fb.group({
      placa: ['', Validators.required],
      tipo: ['', Validators.required],
      marca: ['', Validators.required],
      modelo: ['', Validators.required],
      servicio: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  cargarTiposServicioPorVehiculo(tipoVehiculo: string) {
    this.reservaService.getTiposServicio(tipoVehiculo).subscribe({
      next: (data) => this.tiposServicio = data,
      error: (error) => console.error('Error al cargar tipos de servicio:', error)
    });
  }

  registrarVenta(): void {
    const datos = this.formulario.value;
    const vehiculo = {
      placa: datos.placa,
      tipo: datos.tipo,
      marca: datos.marca,
      modelo: datos.modelo,
    };

    this.vehiculoService.registrarVehiculo(vehiculo).subscribe({
      next: (vehiculoRegistrado) => {
        console.log('Vehículo registrado:', vehiculoRegistrado);
        const fechaActual = new Date().toISOString().split('T')[0];
        const reserva = {
          estado: 'pendiente',
          fechaReserva: fechaActual,
          servicio: datos.servicio,
          vehiculo: { id: vehiculoRegistrado.id }
        };

        this.reservaService.crearReserva(reserva).subscribe({
          next: (res) => {
            this.mensaje = '';
            this.reservaSeleccionada = res;
            this.mostrarFactura = false;
            this.tiposServicio = [];
            console.log('Reserva registrada:', res);
          },
          error: (err) => {
            console.error('Error al crear reserva:', err);
            this.mensaje = 'Error al registrar la reserva.';
          }
        });
      },
      error: (err) => {
        console.error('Error al registrar vehículo:', err);
        this.mensaje = 'Error al registrar el vehículo.';
      }
    });
  }

  mostrarResumenPago(metodo: string): void {
    if (!this.reservaSeleccionada) return;

    this.reservaSeleccionada.metodoPago = metodo;

    this.reservaService.actualizarMetodoPago(this.reservaSeleccionada.id, metodo).subscribe({
      next: () => {
        this.mostrarFactura = true;
      },
      error: (err) => {
        console.error('Error al guardar el método de pago:', err);
        Swal.fire('Error', 'No se pudo guardar el método de pago', 'error');
      }
    });
  }

  nuevaReserva() {
    this.formulario.reset();
    this.reservaSeleccionada = null;
    this.mostrarFactura = false;
    this.metodoPago = '';
    this.mensaje = '';
  }
}
