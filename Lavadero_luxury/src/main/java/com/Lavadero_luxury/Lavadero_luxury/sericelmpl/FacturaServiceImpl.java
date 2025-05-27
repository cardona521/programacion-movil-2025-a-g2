package com.Lavadero_luxury.Lavadero_luxury.sericelmpl;

import com.Lavadero_luxury.Lavadero_luxury.models.Factura;
import com.Lavadero_luxury.Lavadero_luxury.models.Reserva;
import com.Lavadero_luxury.Lavadero_luxury.repository.FacturaRepository;
import com.Lavadero_luxury.Lavadero_luxury.repository.ReservaRepository;
import com.Lavadero_luxury.Lavadero_luxury.services.FacturaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Factura crearFactura(Factura factura) {
        // Cargar la reserva desde la BD usando el ID recibido
        if (factura.getReserva() != null && factura.getReserva().getId() != null) {
            Long reservaId = factura.getReserva().getId();
            Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + reservaId));
            factura.setReserva(reserva);
        }

        return facturaRepository.save(factura);
    }

    @Override
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }
}
