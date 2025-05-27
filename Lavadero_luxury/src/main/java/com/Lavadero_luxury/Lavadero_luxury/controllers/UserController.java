package com.Lavadero_luxury.Lavadero_luxury.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Lavadero_luxury.Lavadero_luxury.models.User;
import com.Lavadero_luxury.Lavadero_luxury.models.Vehiculo;
import com.Lavadero_luxury.Lavadero_luxury.services.UserService;
import com.Lavadero_luxury.Lavadero_luxury.services.VehiculoService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve el mensaje de error
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        boolean success = userService.login(user.getCorreo(), user.getPassword());
        if (success) {
            User userFromDb = userService.findByCorreo(user.getCorreo());
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Inicio de sesión exitoso.");
            response.put("userId", userFromDb.getId());
            response.put("rol", userFromDb.getRol());
            return ResponseEntity.ok(response);
        }
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("mensaje", "Correo o contraseña incorrectos.");
        return ResponseEntity.status(401).body(errorResponse);
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        }
        return ResponseEntity.status(404).body("Usuario no encontrado.");
    }

    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperarContrasena(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");

        if (correo == null || correo.isEmpty()) {
            System.out.println("ERROR: No se ingresó un correo.");
            return ResponseEntity.badRequest().body("El correo es obligatorio");
        }

        System.out.println("Intentando recuperar contraseña para: " + correo);

        boolean enviado = userService.recuperarContrasenia(correo);
        System.out.println("Resultado de recuperar contraseña: " + enviado);

        if (enviado) {
            return ResponseEntity.ok("Se ha enviado la nueva contraseña al correo.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo no está registrado.");
        }
    }
    
    // Agregamos el endpoint faltante que estás intentando usar desde Angular
    @PostMapping("/registroVehiculo")
    public ResponseEntity<?> registroVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            // Registramos el vehículo usando el servicio existente
            Vehiculo nuevoVehiculo = vehiculoService.registrarVehiculo(vehiculo);
            return ResponseEntity.ok(nuevoVehiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}