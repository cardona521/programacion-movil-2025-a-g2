package com.Lavadero_luxury.Lavadero_luxury.sericelmpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lavadero_luxury.Lavadero_luxury.models.Reserva;
import com.Lavadero_luxury.Lavadero_luxury.models.User;
import com.Lavadero_luxury.Lavadero_luxury.models.Vehiculo;
import com.Lavadero_luxury.Lavadero_luxury.repository.ReservaRepository;
import com.Lavadero_luxury.Lavadero_luxury.repository.UserRepository;
import com.Lavadero_luxury.Lavadero_luxury.repository.VehiculoRepository;
import com.Lavadero_luxury.Lavadero_luxury.services.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

@Override
public Reserva crearReserva(Reserva reserva) {
    // Validar que el vehículo sí exista
    if (reserva.getVehiculo() == null || reserva.getVehiculo().getId() == null) {
        throw new IllegalArgumentException("El vehículo es obligatorio");
    }

    // Validar existencia del vehículo
    Optional<Vehiculo> vehiculo = vehiculoRepository.findById(reserva.getVehiculo().getId());
    if (vehiculo.isEmpty()) {
        throw new IllegalArgumentException("Vehículo no encontrado");
    }
    reserva.setVehiculo(vehiculo.get());

    // Si la reserva tiene usuario, validarlo y asociarlo
    if (reserva.getUser() != null && reserva.getUser().getId() != null) {
        Optional<User> usuario = userRepository.findById(reserva.getUser().getId());
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        reserva.setUser(usuario.get());
    } else {
        reserva.setUser(null); // Se permite reserva sin usuario
    }

    return reservaRepository.save(reserva);
}
    @Override
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public boolean deleteReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Reserva> getReservasPorUsuario(Long userId) {
        return reservaRepository.findByUserId(userId);
    }
    
}
