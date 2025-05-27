package com.Lavadero_luxury.Lavadero_luxury.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Lavadero_luxury.Lavadero_luxury.models.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUserId(Long userId);
}

