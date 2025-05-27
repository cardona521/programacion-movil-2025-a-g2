import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component'; // ✅ Import como standalone
import { AppRoutingModule } from './app-routing.module';

import { NavbarComponent } from './components/navbar/navbar.component';
import { RegistroVehiculoComponent } from './registrovehiculo/registrovehiculo.component';
import { ReservaComponent } from './reserva/reserva.component';
import { RegisterComponent } from './registrarse/registrarse.component';
import { PrecioServicioComponent } from './precio-servicio/precio-servicio.component';
import { FooterComponent } from './footer/footer.component';
import { AcercadenosotrosComponent } from './acercadenosotros/acercadenosotros.component';
import { GestionVentasComponent } from './gestionventas/gestionventas.component';
import { InicioSesionComponent } from './inicio-sesion/inicio-sesion.component';
import { InicioComponent } from './inicio/inicio.component';
import { HeaderComponent } from './header/header.component';
import { HistorialComponent } from './historial/historial.component';
import { FacturaComponent } from './factura/factura.component';
import { PerfilComponent } from './perfil/perfil.component';

@NgModule({
  declarations: [
    NavbarComponent,
    RegistroVehiculoComponent,
    ReservaComponent,
    RegisterComponent,
    PrecioServicioComponent,
    FooterComponent,
    AcercadenosotrosComponent,
    GestionVentasComponent,
    InicioSesionComponent,
    InicioComponent,
    HeaderComponent,
    HistorialComponent,
    FacturaComponent,
    AppComponent,
    PerfilComponent,
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    
  ],
  providers: [{ provide: RouteReuseStrategy, useClass: IonicRouteStrategy }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent],
})
export class AppModule {}
