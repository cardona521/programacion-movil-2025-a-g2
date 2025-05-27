package com.Lavadero_luxury.Lavadero_luxury.services;

import com.Lavadero_luxury.Lavadero_luxury.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User registerUser(User user);
    String hashContrasenia(String password);
    User findByCorreo(String correo);
    boolean validatePassword(String rawPassword, String encryptedPassword);
    User updateUser(Long id, User user);
    Optional<User> getUserById(Long id);
    boolean login(String correo, String password);
    boolean deleteUser(Long id);
    boolean recuperarContrasenia(String correo);
    void enviarCorreo(String correo, String nuevaContrasenia); // 👈 Agregado
}

