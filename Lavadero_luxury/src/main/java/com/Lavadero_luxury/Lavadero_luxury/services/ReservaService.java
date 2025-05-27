package com.Lavadero_luxury.Lavadero_luxury.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Lavadero_luxury.Lavadero_luxury.models.Reserva;

@Service
public interface ReservaService {
    Reserva crearReserva(Reserva reserva);
    List<Reserva> getAllReservas();
    Optional<Reserva> getReservaById(Long id);
    boolean deleteReserva(Long id);
    List<Reserva> getReservasPorUsuario(Long userId);
}
