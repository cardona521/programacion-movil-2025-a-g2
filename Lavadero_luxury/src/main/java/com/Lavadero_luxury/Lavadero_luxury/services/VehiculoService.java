package com.Lavadero_luxury.Lavadero_luxury.services;

import com.Lavadero_luxury.Lavadero_luxury.models.Vehiculo;

import java.util.List;
import java.util.Optional;

public interface VehiculoService {
    Vehiculo registrarVehiculo(Vehiculo vehiculo);
    List<Vehiculo> obtenerTodosPorIdUsuario(Integer id);
    List<Vehiculo> obtenerTodos();
    Optional<Vehiculo> obtenerPorId(Long id);
    Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculo);
    boolean eliminarVehiculo(Long id);
}
