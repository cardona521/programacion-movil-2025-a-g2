package com.Lavadero_luxury.Lavadero_luxury.controllers;

import com.Lavadero_luxury.Lavadero_luxury.models.Reserva;
import com.Lavadero_luxury.Lavadero_luxury.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/crear")
public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva) {
    try {
        // Extraer el precio del string "servicio"
        String servicio = reserva.getServicio();
        if (servicio != null) {
            Pattern pattern = Pattern.compile("\\$(\\d{1,3}(?:\\.\\d{3})*)(?=\\s|$)");
            Matcher matcher = pattern.matcher(servicio);
            if (matcher.find()) {
                String precioStr = matcher.group(1); // e.g., "45.000"
                precioStr = precioStr.replace(".", ""); // quitar puntos
                double precio = Double.parseDouble(precioStr);
                reserva.setPrecio(precio);
            }
        }

        // Establecer la fecha actual en zona horaria de Colombia
        LocalDateTime fechaColombia = LocalDateTime.now(ZoneId.of("America/Bogota"));
        reserva.setFechaCreacion(fechaColombia);

        // Crear la reserva
        Reserva nuevaReserva = reservaService.crearReserva(reserva);
        return ResponseEntity.ok(nuevaReserva);

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Error en los datos: " + e.getMessage());
    } catch (RuntimeException e) {
        return ResponseEntity.internalServerError().body("Error del servidor: " + e.getMessage());
    }
}


    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.getAllReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);
        return reserva.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
    boolean eliminada = reservaService.deleteReserva(id);
    if (eliminada) {
        return ResponseEntity.ok(Map.of("mensaje", "Reserva eliminada correctamente"));
    } else {
        return ResponseEntity.status(404).body(Map.of("error", "Reserva no encontrada"));
    }
}

    @GetMapping("/tipos-servicio")
public List<String> obtenerTiposPorVehiculo(@RequestParam String tipoVehiculo) {
    switch (tipoVehiculo.toLowerCase()) {
        case "automovil":
        case "carro":  // Añadir este caso
            return List.of("Lavado Express $100.000", "Lavado a vapor $110.000", "Lavado completo $100.000" , "Lavado de motor $90.000", "Lavado interno $80.000");
        case "moto":
            return List.of("Lavado básico moto $50.000", "Lavado cadena $20.000", "Engrase $30.000");
        case "bus":
            return List.of("Lavado general bus $50.000", "Limpieza de asientos $70.000");
        default:
            return List.of();
    }
}
@GetMapping("/usuario/{userId}")
public ResponseEntity<List<Reserva>> getReservasPorUsuario(@PathVariable Long userId) {
    List<Reserva> reservas = reservaService.getReservasPorUsuario(userId);
    return ResponseEntity.ok(reservas);
}

@PutMapping("/{id}/metodo-pago")
public ResponseEntity<?> actualizarMetodoPago(@PathVariable Long id, @RequestBody String metodoPago) {
    Optional<Reserva> optionalReserva = reservaService.getReservaById(id);
    if (optionalReserva.isPresent()) {
        Reserva reserva = optionalReserva.get();
        reserva.setMetodoPago(metodoPago);
        reservaService.crearReserva(reserva); // reutiliza el método para guardar
        return ResponseEntity.ok().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

@PutMapping("/{id}/estado")
public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
    Optional<Reserva> optionalReserva = reservaService.getReservaById(id);
    if (optionalReserva.isPresent()) {
        Reserva reserva = optionalReserva.get();
        String estado = body.get("estado"); // extrae el campo del JSON
        reserva.setEstado(estado);
        reservaService.crearReserva(reserva); // reutiliza el método para guardar
        return ResponseEntity.ok().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}


}
