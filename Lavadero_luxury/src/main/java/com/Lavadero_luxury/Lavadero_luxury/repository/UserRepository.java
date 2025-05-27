package com.Lavadero_luxury.Lavadero_luxury.repository;

import com.Lavadero_luxury.Lavadero_luxury.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Long id);
    Optional<User> findByCelular(String celular);

    Optional<User> findByCorreo(String correo);


    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<User> findByCedulaAndTipoCedula(String cedula, String tipoCedula);
}
