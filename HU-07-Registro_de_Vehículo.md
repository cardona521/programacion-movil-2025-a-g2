# HU-07: Registro de vehículo

**Como** cliente registrado  
**Quiero** poder registrar mis vehículos en el sistema  
**Para** posteriormente poder reservar servicios para ellos.

###  Criterios de Aceptación:

- El formulario solicita: placa, color, modelo, marca y tipo de vehículo (carro, moto, bus).
- El sistema valida que la placa no esté duplicada para el mismo usuario.
- El vehículo queda asociado al usuario que lo registra.
- Se muestra confirmación después del registro exitoso.
- Los vehículos registrados aparecen en una lista para el usuario.

---

###  Definición de Listo (DoR - Definition of Ready):

- Se han definido los campos necesarios para el registro de vehículos.
- Se han establecido las validaciones requeridas para cada campo.
- Se ha diseñado la estructura de datos para almacenar la información.
- Se ha definido la relación entre usuarios y vehículos en la base de datos.

---

###  Definición de Hecho (DoD - Definition of Done):

- El formulario de registro de vehículos funciona correctamente.
- Las validaciones previenen el ingreso de datos incorrectos o duplicados.
- Los vehículos se asocian correctamente al usuario que los registra.
- El usuario puede ver la lista de sus vehículos registrados.
- Las pruebas unitarias y de integración han sido ejecutadas exitosamente.

---

###  Prioridad y Estimación:

- **Prioridad:** Alta
- **Estimación:** 3 días

### codigo para el desarrollo de esta HU

package com.Lavadero_luxury.Lavadero_luxury.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;   

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reserva> reservas;
    
    // toString
    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", tipo='" + tipo + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo)) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(id, vehiculo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
