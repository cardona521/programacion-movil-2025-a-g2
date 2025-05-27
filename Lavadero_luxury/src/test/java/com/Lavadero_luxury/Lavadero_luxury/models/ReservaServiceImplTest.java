package com.Lavadero_luxury.Lavadero_luxury.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Lavadero_luxury.Lavadero_luxury.repository.ReservaRepository;
import com.Lavadero_luxury.Lavadero_luxury.repository.UserRepository;
import com.Lavadero_luxury.Lavadero_luxury.repository.VehiculoRepository;
import com.Lavadero_luxury.Lavadero_luxury.sericelmpl.ReservaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceImplTest {

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testCrearReservaCorrecta() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        User user = new User();
        user.setId(Integer.valueOf(2));

        Reserva reserva = new Reserva();
        reserva.setVehiculo(vehiculo);
        reserva.setUser(user);

        Mockito.when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        Mockito.when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.crearReserva(reserva);

        assertNotNull(resultado);
        assertEquals(vehiculo, resultado.getVehiculo());
        assertEquals(user, resultado.getUser());
    }

    @Test
    void testCrearReservaVehiculoNoExiste() {
        Reserva reserva = new Reserva();
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(10L);
        reserva.setVehiculo(vehiculo);

        Mockito.when(vehiculoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservaService.crearReserva(reserva));
    }
}

