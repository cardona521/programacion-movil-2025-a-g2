package com.Lavadero_luxury.Lavadero_luxury.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Lavadero_luxury.Lavadero_luxury.models.Vehiculo;
import com.Lavadero_luxury.Lavadero_luxury.services.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevoVehiculo = vehiculoService.registrarVehiculo(vehiculo);
            return ResponseEntity.ok(nuevoVehiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodosLosVehiculos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorId(@PathVariable Long id) {
        Optional<Vehiculo> vehiculo = vehiculoService.obtenerPorId(id);
        return vehiculo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/u/{id}")
    public ResponseEntity<List<Vehiculo>> obtenerTodosPorIdUsuario(@PathVariable Integer id) {
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodosPorIdUsuario(id);
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehiculos); 
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        Vehiculo actualizado = vehiculoService.actualizarVehiculo(id, vehiculo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVehiculo(@PathVariable Long id) {
        boolean eliminado = vehiculoService.eliminarVehiculo(id);
        if (eliminado) {
            return ResponseEntity.ok("Vehículo eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("Vehículo no encontrado.");
    }
}
