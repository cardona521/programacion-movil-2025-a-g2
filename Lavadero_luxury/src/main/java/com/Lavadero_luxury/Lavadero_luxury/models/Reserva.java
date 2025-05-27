package com.Lavadero_luxury.Lavadero_luxury.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private String estado;


    @Column(name = "fecha_reserva")
    private String fechaReserva;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    
    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    @JsonIgnore
    private Factura factura;



    // Getters y Setters

    public String getMetodoPago() {
    return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
    this.metodoPago = metodoPago;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
        }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    

    // toString
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", fechaReserva='" + fechaReserva + '\'' +
                ", servicio='" + servicio + '\'' +
                '}';
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
}
