import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { AppModule } from '../app.module';
import html2canvas from 'html2canvas';
import { jsPDF } from 'jspdf';
import { IonicModule } from '@ionic/angular';
import { FacturaService } from '../services/factura.service';
import { HistorialService } from '../services/historial.service';
import { ReservaService } from '../services/reserva.service';
@Component({
  selector: 'app-factura',
  standalone: false,
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.scss']
})
export class FacturaComponent implements OnInit {

  reservas: any[] = [];
  reservasOriginal: any[] = [];

  reservaSeleccionada: any = null;
  metodoPago: string = '';
  metodosPago: string[] = ['Efectivo', 'Tarjeta', 'Transferencia'];

  productosLavadero = [
    { nombre: 'Papas', precio: 3000 },
    { nombre: 'Gaseosa', precio: 3500 },
    { nombre: 'Torta', precio: 5000 },
    { nombre: 'Agua', precio: 2000 }
  ];

  productosSeleccionados: any[] = [];
  mostrarDetalle = false;
  mostrarFacturaFinal = false;

  filtros = {
    fechaInicio: '',
    fechaFin: '',
    placa: '',
    documento: '',
    tipoVehiculo: '',
    servicio: ''
  };

  constructor(
    private reservaService: ReservaService,
    private facturaService: FacturaService,
    private historialService: HistorialService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cargarReserva(Number(id));
    }
    this.getAllReservas();
  }

  getAllReservas(): void {
    this.reservaService.getReservas().subscribe({
      next: (data) => {
        this.reservas = data;
        this.reservasOriginal = [...data]; // copia la lista original
      },
      error: (err) => console.error('Error cargando reservas:', err)
    });
  }

  cargarReserva(id: number): void {
    this.reservaService.getReservaPorId(id).subscribe({
      next: (reserva) => {
        this.reservaSeleccionada = reserva;
      },
      error: (err) => console.error('Error al cargar la reserva:', err)
    });
  }

  aplicarFiltros(): void {
    this.reservas = this.reservasOriginal.filter((reserva) => {
      const fechaReserva = new Date(reserva.fechaReserva);
      const fechaInicio = this.filtros.fechaInicio ? new Date(this.filtros.fechaInicio) : null;
      const fechaFin = this.filtros.fechaFin ? new Date(this.filtros.fechaFin) : null;

      if (fechaInicio && fechaReserva < fechaInicio) return false;
      if (fechaFin && fechaReserva > fechaFin) return false;
      if (this.filtros.placa && !reserva.vehiculo?.placa.toLowerCase().includes(this.filtros.placa.toLowerCase())) return false;
      if (this.filtros.documento && !reserva.user?.documento?.toLowerCase().includes(this.filtros.documento.toLowerCase())) return false;
      if (this.filtros.tipoVehiculo && reserva.vehiculo?.tipo !== this.filtros.tipoVehiculo) return false;
      if (this.filtros.servicio && reserva.servicio !== this.filtros.servicio) return false;

      return true;
    });
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

  actualizarProductosSeleccionados(event: any, producto: any) {
    if (event.target.checked) {
      this.productosSeleccionados.push(producto);
    } else {
      this.productosSeleccionados = this.productosSeleccionados.filter(p => p.nombre !== producto.nombre);
    }
  }

  calcularTotal(): number {
    if (!this.reservaSeleccionada) return 0;
    const precioServicio = this.reservaSeleccionada.precio || 0;
    const totalProductos = this.productosSeleccionados.reduce((acc, prod) => acc + prod.precio, 0);
    return precioServicio + totalProductos;
  }

  verDetalle(reserva: any): void {
    this.reservaSeleccionada = reserva;
    this.mostrarDetalle = true;
    this.mostrarFacturaFinal = false;
    this.metodoPago = reserva.metodoPago || '';
    this.productosSeleccionados = [];
  }

  cerrarDetalle(): void {
    this.mostrarDetalle = false;
    this.reservaSeleccionada = null;
    this.metodoPago = '';
    this.productosSeleccionados = [];
  }

  generarFactura(): void {
    if (!this.reservaSeleccionada.facturada) {
      const factura = {
        reserva: { id: this.reservaSeleccionada.id },
        metodoPago: this.metodoPago,
        total: this.calcularTotal(),
        detalleProductos: this.productosSeleccionados.map(p => `${p.nombre}: ${p.precio}`).join(', ')
      };

      this.facturaService.generarFactura(factura).subscribe({
        next: (res) => {
          this.reservaSeleccionada.facturada = true;

          this.historialService.actualizarEstadoReserva(this.reservaSeleccionada.id, 'Pagado').subscribe({
            next: () => console.log('Reserva marcada como pagada'),
            error: (e) => console.error('Error al actualizar estado de reserva:', e)
          });

          this.mostrarDetalle = false;
          this.mostrarFacturaFinal = true;
        },
        error: (err) => console.error('Error al generar factura:', err)
      });
    }
  }

  exportarPDF(): void {
    const data = document.getElementById('facturaPDF');
    if (!data) return;

    html2canvas(data).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF('p', 'mm', 'a4');
      const imgProps = pdf.getImageProperties(imgData);
      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

      pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
      pdf.save(`factura_${this.reservaSeleccionada.id}.pdf`);
    });
  }

  cerrarFacturaFinal(): void {
    this.mostrarFacturaFinal = false;
    this.reservaSeleccionada = null;
    this.metodoPago = '';
    this.productosSeleccionados = [];
  }

  getFechaActual(): string {
    return new Date().toLocaleDateString('es-CO');
  }
}
