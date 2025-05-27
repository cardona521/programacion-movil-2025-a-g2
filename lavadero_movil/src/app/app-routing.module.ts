import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import { InicioComponent } from './inicio/inicio.component';
import { InicioSesionComponent } from './inicio-sesion/inicio-sesion.component';
import { RegisterComponent } from './registrarse/registrarse.component';
import { PrecioServicioComponent } from './precio-servicio/precio-servicio.component';
import { RegistroVehiculoComponent } from './registrovehiculo/registrovehiculo.component';
import { PerfilComponent } from './perfil/perfil.component';
import { ReservaComponent } from './reserva/reserva.component';
import { HistorialComponent } from './historial/historial.component';
import { FacturaComponent } from './factura/factura.component';
import { GestionVentasComponent } from './gestionventas/gestionventas.component';
import { AcercadenosotrosComponent } from './acercadenosotros/acercadenosotros.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'inicio-sesion', component: InicioSesionComponent },
  { path: 'registrarse', component: RegisterComponent },
  { path: 'precio-servicio', component: PrecioServicioComponent },
  { path: 'registro-vehiculo', component: RegistroVehiculoComponent },
  { path: 'perfil', component: PerfilComponent },
  { path: 'reserva', component: ReservaComponent },
  { path: 'historial', component: HistorialComponent },
  { path: 'factura', component: FacturaComponent },
  { path: 'gestionventas', component: GestionVentasComponent },
  { path: 'nosotros', component: AcercadenosotrosComponent },
  { path: 'header', component: HeaderComponent },
  { path: 'footer', component: FooterComponent },
  { path: 'acercadenosotros', component: AcercadenosotrosComponent } // Redirigir a inicio si la ruta no coincide
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
