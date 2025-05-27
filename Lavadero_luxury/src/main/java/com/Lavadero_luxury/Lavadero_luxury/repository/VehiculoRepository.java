package com.Lavadero_luxury.Lavadero_luxury.repository;

import com.Lavadero_luxury.Lavadero_luxury.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByUser_Id(Integer id);
}
