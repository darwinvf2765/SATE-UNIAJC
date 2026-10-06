package co.uniajc.sate.service;

import co.uniajc.sate.model.Usuario;
import co.uniajc.sate.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario validarCredenciales(String email, String password) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Credenciales inválidas"
                ));

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return usuario;
    }

    public String generarTokenRecuperacion(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuario no encontrado"
                ));

        String token = UUID.randomUUID().toString();

        usuario.setResetToken(token);
        usuario.setResetTokenExpiry(
                LocalDateTime.now().plusMinutes(15)
        );

        usuarioRepository.save(usuario);

        return token;
    }

    public void restablecerPassword(
            String token,
            String nuevaPassword) {

        Usuario usuario = usuarioRepository
                .findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Token inválido"
                ));

        if (usuario.getResetTokenExpiry() == null ||
                usuario.getResetTokenExpiry().isBefore(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "Token expirado"
            );
        }

        usuario.setPasswordHash(
                passwordEncoder.encode(nuevaPassword)
        );

        usuario.setResetToken(null);
        usuario.setResetTokenExpiry(null);

        usuarioRepository.save(usuario);
    }
}