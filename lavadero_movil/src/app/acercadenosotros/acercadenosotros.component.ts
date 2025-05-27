import { Component } from '@angular/core';
import { AppModule } from '../app.module';

@Component({
  selector: 'app-acercadenosotros',
  templateUrl: './acercadenosotros.component.html',
  styleUrls: ['./acercadenosotros.component.scss'],
  standalone: false,
})
export class AcercadenosotrosComponent {
  titulo = 'Lavadero Luxury';
  descripcion = 'En Lavadero Luxury nos especializamos en el cuidado integral de tu vehículo. Ofrecemos un servicio de limpieza y mantenimiento detallado para automóviles, motos y buses, garantizando excelencia y satisfacción.';
  mision = 'Brindar un servicio de lavado de vehículos con altos estándares de calidad, eficiencia y atención personalizada, garantizando la satisfacción total de nuestros clientes.';
  vision = 'Ser reconocidos como el mejor lavadero de vehículos a nivel regional, destacando por la excelencia en el servicio, innovación tecnológica y compromiso con el medio ambiente.';
}
