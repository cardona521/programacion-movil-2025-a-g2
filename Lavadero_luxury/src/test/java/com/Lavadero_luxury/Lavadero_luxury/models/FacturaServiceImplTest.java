package com.Lavadero_luxury.Lavadero_luxury.models;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Lavadero_luxury.Lavadero_luxury.repository.FacturaRepository;
import com.Lavadero_luxury.Lavadero_luxury.repository.ReservaRepository;
import com.Lavadero_luxury.Lavadero_luxury.sericelmpl.FacturaServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceImplTest {

    @InjectMocks
    private FacturaServiceImpl facturaService;

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Test
    void testCrearFacturaConReservaExistente() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);

        Factura factura = new Factura();
        factura.setReserva(reserva);

        Mockito.when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        Mockito.when(facturaRepository.save(factura)).thenReturn(factura);

        Factura resultado = facturaService.crearFactura(factura);

        assertEquals(reserva, resultado.getReserva());
        Mockito.verify(facturaRepository).save(factura);
    }

    @Test
    void testObtenerFacturaPorId() {
        Factura factura = new Factura();
        factura.setId(1L);

        Mockito.when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));

        Factura resultado = facturaService.obtenerFacturaPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }
}

