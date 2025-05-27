package com.Lavadero_luxury.Lavadero_luxury.sericelmpl;

import com.Lavadero_luxury.Lavadero_luxury.models.User;
import com.Lavadero_luxury.Lavadero_luxury.repository.UserRepository;
import com.Lavadero_luxury.Lavadero_luxury.services.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
public User registerUser(User user) {
    // 1. Validar que ningún campo obligatorio esté vacío o nulo
    if (user.getNombre() == null || user.getNombre().trim().isEmpty()) {
        throw new RuntimeException("El nombre no puede estar vacío.");
    }

    if (user.getCorreo() == null || user.getCorreo().trim().isEmpty()) {
        throw new RuntimeException("El correo no puede estar vacío.");
    }

    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
        throw new RuntimeException("La contraseña no puede estar vacía.");
    }

    if (user.getCelular() == null || user.getCelular().trim().isEmpty()) {
        throw new RuntimeException("El número de celular no puede estar vacío.");
    }

    if (user.getCedula() == null || user.getCedula().trim().isEmpty()) {
        throw new RuntimeException("La cédula no puede estar vacía.");
    }

    if (user.getTipoCedula() == null || user.getTipoCedula().trim().isEmpty()) {
        throw new RuntimeException("Debe seleccionar un tipo de cédula.");
    }

    if (user.getRol() == null || 
        (!user.getRol().equalsIgnoreCase("cliente") && !user.getRol().equalsIgnoreCase("admin"))) {
        throw new RuntimeException("Debe seleccionar un rol válido (cliente o admin).");
    }

    // 2. Validaciones de duplicados (solo si todos los campos requeridos están completos)
    if (userRepository.findByCorreo(user.getCorreo()).isPresent()) {
        throw new RuntimeException("El correo ya está en uso.");
    }

    if (userRepository.findByCelular(user.getCelular()).isPresent()) {
        throw new RuntimeException("El número de celular ya está en uso.");
    }

    if (userRepository.findByCedulaAndTipoCedula(user.getCedula(), user.getTipoCedula()).isPresent()) {
        throw new RuntimeException("Ya existe un usuario con esta cédula y tipo de cédula.");
    }

    // 3. Encriptar y guardar
    user.setPassword(hashContrasenia(user.getPassword()));
    return userRepository.save(user);
}

   

    @Override
    public boolean login(String correo, String password) {
        Optional<User> user = userRepository.findByCorreo(correo);
        return user.isPresent() && user.get().getPassword().equals(hashContrasenia(password));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setNombre(user.getNombre());
            existingUser.setCorreo(user.getCorreo());
            existingUser.setPassword(hashContrasenia(user.getPassword()));
            return userRepository.save(existingUser);
        }).orElse(null);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public String hashContrasenia(String password) {
        try {
            MessageDigest instancia = MessageDigest.getInstance("SHA-256");
            byte[] hash = instancia.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña");
        }
    }

    /**
     * Método para recuperar la contraseña de un usuario.
     */
    public boolean recuperarContrasenia(String correo) {
        Optional<User> userOpt = userRepository.findByCorreo(correo);

        if (userOpt.isEmpty()) {
            System.out.println("ERROR: No se encontró usuario con correo: " + correo);
            return false;
        }

        User user = userOpt.get();
        String nuevaContrasena = generarNuevaContrasena();
        System.out.println("Nueva contraseña generada: " + nuevaContrasena);
        user.setPassword(hashContrasenia(nuevaContrasena)); // Encripta la nueva contraseña
        userRepository.save(user);

        enviarCorreo(correo, nuevaContrasena);
        return true;
    }

    /**
     * Método para generar una nueva contraseña aleatoria.
     */
    private String generarNuevaContrasena() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Método para enviar un correo con la nueva contraseña.
     */
    @Override
    public void enviarCorreo(String correo, String nuevaContrasenia) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(correo);
            helper.setSubject("🔑 Recuperación de Contraseña - Lavadero Luxury");
            helper.setText(
                "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd; border-radius: 10px; max-width: 600px;'>" +
                "<h2 style='color: #4CAF50;'>Lavadero Luxury 🚗✨</h2>" +
                "<p>Hola,</p>" +
                "<p>Hemos recibido tu solicitud de recuperación de contraseña.</p>" +
                "<p>Tu nueva contraseña es:</p>" +
                "<p style='font-size: 18px; font-weight: bold; color: #333; background: #f4f4f4; padding: 10px; border-radius: 5px;'>" + nuevaContrasenia + "</p>" +
                "<p>Te recomendamos cambiarla después de iniciar sesión.</p>" +
                "<br>" +
                "<p>Saludos,<br><strong>Lavadero Luxury</strong></p>" +
                "</div>",
                true
            );

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
    

    @Override
    public User findByCorreo(String correo) {
        return userRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }    

    @Override
    public boolean validatePassword(String rawPassword, String encryptedPassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validatePassword'");
    }
}
