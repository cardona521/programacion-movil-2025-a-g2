package com.Lavadero_luxury.Lavadero_luxury.services;

import com.Lavadero_luxury.Lavadero_luxury.models.Factura;
import java.util.List;

public interface FacturaService {
    Factura crearFactura(Factura factura);
    List<Factura> listarFacturas();
    Factura obtenerFacturaPorId(Long id);
}
