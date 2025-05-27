import { Component, OnInit } from '@angular/core';
import { HistorialService } from '../services/historial.service';
import { ReservaService } from '../services/reserva.service';
@Component({
  selector: 'app-historial',
  templateUrl: './historial.component.html',
  styleUrls: ['./historial.component.scss'],
  standalone: false,
})
export class HistorialComponent implements OnInit {
  filtros = {
    fechaInicio: '',
    fechaFin: '',
    placa: '',
    documento: '',
    tipoVehiculo: '',
    servicio: ''
  };

  reservasOriginal: any[] = [];  
  reservas: any[] = [];          
  isAdmin: boolean = false;
  userId: number = 0;

  constructor(
    private reservaService: ReservaService,
    private historialService: HistorialService
  ) {}

  ngOnInit(): void {
    const rol = localStorage.getItem('rol');
    const id = localStorage.getItem('userId');

    this.isAdmin = rol?.toUpperCase() === 'ADMIN';
    this.userId = id ? Number(id) : 0;

    if (this.isAdmin) {
      this.getAllReservas();
    } else {
      this.getReservasByUser();
    }
  }

  getAllReservas(): void {
    this.reservaService.getReservas().subscribe({
      next: (data) => {
        this.reservasOriginal = data;
        this.reservas = [...data];
      },
      error: (err) => console.error('Error cargando reservas:', err)
    });
  }

  getReservasByUser(): void {
    this.historialService.getReservasPorUsuario(this.userId).subscribe({
      next: (data: any[]) => {
        this.reservasOriginal = data.map((reserva) => ({
          ...reserva,
          vehiculo: {
            ...reserva.vehiculo,
            tipo: reserva.vehiculo?.tipo || ''
          },
          metodoPago: reserva.metodoPago || 'No registrado'
        }));
        this.reservas = [...this.reservasOriginal];
      },
      error: (err) => console.error('Error cargando reservas del usuario:', err)
    });
  }

  aplicarFiltros(): void {
    let reservasFiltradas = [...this.reservasOriginal];

    if (this.filtros.fechaInicio) {
      reservasFiltradas = reservasFiltradas.filter(r =>
        new Date(r.fechaReserva) >= new Date(this.filtros.fechaInicio)
      );
    }

    if (this.filtros.fechaFin) {
      reservasFiltradas = reservasFiltradas.filter(r =>
        new Date(r.fechaReserva) <= new Date(this.filtros.fechaFin)
      );
    }

    if (this.filtros.placa) {
      reservasFiltradas = reservasFiltradas.filter(r =>
        r.vehiculo?.placa?.toLowerCase().includes(this.filtros.placa.toLowerCase())
      );
    }

    if (this.filtros.documento) {
      reservasFiltradas = reservasFiltradas.filter(r =>
        r.user?.documento?.toString().includes(this.filtros.documento)
      );
    }

    if (this.filtros.tipoVehiculo) {
      reservasFiltradas = reservasFiltradas.filter(r =>
        r.vehiculo?.tipo === this.filtros.tipoVehiculo
      );
    }

    if (this.filtros.servicio) {
      reservasFiltradas = reservasFiltradas.filter(r =>
        r.servicio === this.filtros.servicio
      );
    }

    this.reservas = reservasFiltradas;
  }

  resetFiltros(): void {
    this.filtros = {
      fechaInicio: '',
      fechaFin: '',
      placa: '',
      documento: '',
      tipoVehiculo: '',
      servicio: ''
    };
    this.reservas = [...this.reservasOriginal];
  }

  eliminarReserva(id: number): void {
    this.historialService.deleteReserva(id).subscribe({
      next: () => {
        this.reservas = this.reservas.filter((reserva) => reserva.id !== id);
        console.log('Reserva eliminada:', id);
      },
      error: (err) => console.error('Error eliminando reserva:', err)
    });
  }

  actualizarReserva(id: number, estado: string): void {
    this.historialService.actualizarEstadoReserva(id, estado).subscribe({
      next: () => {
        if (this.isAdmin) {
          this.getAllReservas();
        } else {
          this.getReservasByUser();
        }
      },
      error: (err) => console.error('Error actualizando reserva:', err)
    });
  }
}
