package com.Lavadero_luxury.Lavadero_luxury.sericelmpl;

import com.Lavadero_luxury.Lavadero_luxury.models.Vehiculo;
import com.Lavadero_luxury.Lavadero_luxury.repository.VehiculoRepository;
import com.Lavadero_luxury.Lavadero_luxury.services.VehiculoService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    @Transactional
    public List<Vehiculo> obtenerTodosPorIdUsuario(Integer user_id) {
        return vehiculoRepository.findByUser_Id(user_id);
    }


    @Override
    public Optional<Vehiculo> obtenerPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculo) {
        Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(id);
        if (vehiculoExistente.isPresent()) {
            Vehiculo v = vehiculoExistente.get();
            v.setMarca(vehiculo.getMarca());
            v.setModelo(vehiculo.getModelo());
            v.setPlaca(vehiculo.getPlaca());
            v.setColor(vehiculo.getColor());
            v.setTipo(vehiculo.getTipo());
            return vehiculoRepository.save(v);
        }
        return null;
    }

    @Override
    public boolean eliminarVehiculo(Long id) {
        if (vehiculoRepository.existsById(id)) {
            vehiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
}
