package com.Lavadero_luxury.Lavadero_luxury.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.Lavadero_luxury.Lavadero_luxury.repository.UserRepository;
import com.Lavadero_luxury.Lavadero_luxury.sericelmpl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender mailSender;

    @Test
    void testRegisterUserConDatosValidos() {
        User user = new User();
        user.setNombre("Daniel");
        user.setCorreo("daniel@correo.com");
        user.setPassword("123456");
        user.setCelular("3101234567");
        user.setCedula("1000001");
        user.setTipoCedula("CC");
        user.setRol("cliente");

        when(userRepository.findByCorreo(user.getCorreo())).thenReturn(Optional.empty());
        when(userRepository.findByCelular(user.getCelular())).thenReturn(Optional.empty());
        when(userRepository.findByCedulaAndTipoCedula(user.getCedula(), user.getTipoCedula())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registrado = userService.registerUser(user);

        assertNotNull(registrado);
        assertEquals("Daniel", registrado.getNombre());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLoginCorrecto() {
        String correo = "test@example.com";
        String contrasena = "1234";
        String contrasenaHash = userService.hashContrasenia(contrasena);

        User user = new User();
        user.setCorreo(correo);
        user.setPassword(contrasenaHash);

        when(userRepository.findByCorreo(correo)).thenReturn(Optional.of(user));

        assertTrue(userService.login(correo, contrasena));
    }

    @Test
    void testRecuperarContraseniaUsuarioInexistente() {
        when(userRepository.findByCorreo("noexiste@email.com")).thenReturn(Optional.empty());
        boolean resultado = userService.recuperarContrasenia("noexiste@email.com");
        assertFalse(resultado);
    }
}

