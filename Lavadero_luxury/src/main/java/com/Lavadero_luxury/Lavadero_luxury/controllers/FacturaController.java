package com.Lavadero_luxury.Lavadero_luxury.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Lavadero_luxury.Lavadero_luxury.models.Factura;
import com.Lavadero_luxury.Lavadero_luxury.models.Reserva;
import com.Lavadero_luxury.Lavadero_luxury.repository.ReservaRepository;
import com.Lavadero_luxury.Lavadero_luxury.services.FacturaService;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")

public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ReservaRepository reservaRepository;

    // POST: Registrar factura
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarFactura(@RequestBody Factura factura) {
        try {
            // Validación de datos, asegurando que la reserva y el metodo de pago estén presentes
            if (factura.getReserva() == null || factura.getMetodoPago() == null || factura.getMetodoPago().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Los datos de la factura son incompletos. Reserva o método de pago faltantes.");
            }

            // Llamamos al servicio para crear la factura
            Factura nueva = facturaService.crearFactura(factura);

            // Devolver respuesta exitosa con la factura creada
            return ResponseEntity.ok(nueva);
        } catch (Exception e) {
            // Manejo de errores generando una respuesta adecuada
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al registrar la factura: " + e.getMessage());
        }
    }

    // GET: Listar todas las facturas
    @GetMapping("/listar")
    public ResponseEntity<List<Factura>> listarFacturas() {
        try {
            List<Factura> facturas = facturaService.listarFacturas();
            return ResponseEntity.ok(facturas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET: Obtener una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        try {
            Optional<Factura> factura = Optional.ofNullable(facturaService.obtenerFacturaPorId(id));
            return factura.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.status(404).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la factura: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/finalizar")
public ResponseEntity<Reserva> finalizarReserva(@PathVariable Long id) {
    Optional<Reserva> optionalReserva = reservaRepository.findById(id);
    if (optionalReserva.isPresent()) {
        Reserva reserva = optionalReserva.get();
        reserva.setEstado("Finalizado"); // o estado = true, según tu modelo
        reservaRepository.save(reserva);
        return ResponseEntity.ok(reserva);
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
