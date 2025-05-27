package com.Lavadero_luxury.Lavadero_luxury.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    private String metodoPago;

    private Double total;

    @Lob
    @Column(name = "detalle_productos", columnDefinition = "TEXT")
    private String detalleProductos; // Ej: "Aceite: 15000, Shampoo: 8000"

    private LocalDateTime fechaCreacion;

    public Factura() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Factura(Reserva reserva, String metodoPago, Double total, String detalleProductos) {
        this.reserva = reserva;
        this.metodoPago = metodoPago;
        this.total = total;
        this.detalleProductos = detalleProductos;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() { return id; }

    public Reserva getReserva() { return reserva; }

    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public String getMetodoPago() { return metodoPago; }

    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public Double getTotal() { return total; }

    public void setTotal(Double total) { this.total = total; }

    public String getDetalleProductos() { return detalleProductos; }

    public void setDetalleProductos(String detalleProductos) { this.detalleProductos = detalleProductos; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public void setId(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
