package co.uniajc.sate.config;

import co.uniajc.sate.model.Rol;
import co.uniajc.sate.model.Usuario;
import co.uniajc.sate.repository.RolRepository;
import co.uniajc.sate.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner inicializarDatos(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            Rol docente = crearRol(rolRepository, "DOCENTE");
            Rol estudiante = crearRol(rolRepository, "ESTUDIANTE");
            Rol director = crearRol(rolRepository, "DIRECTOR");

            crearUsuario(
                    usuarioRepository,
                    passwordEncoder,
                    "docente@uniajc.edu.co",
                    "123456",
                    docente
            );

            crearUsuario(
                    usuarioRepository,
                    passwordEncoder,
                    "estudiante@uniajc.edu.co",
                    "123456",
                    estudiante
            );

            crearUsuario(
                    usuarioRepository,
                    passwordEncoder,
                    "director@uniajc.edu.co",
                    "123456",
                    director
            );
        };
    }

    private Rol crearRol(RolRepository repository, String nombre) {
        return repository.findByNombre(nombre)
                .orElseGet(() -> repository.save(new Rol(nombre)));
    }

    private void crearUsuario(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder,
            String email,
            String password,
            Rol rol) {

        if (repository.findByEmail(email).isEmpty()) {
            Usuario usuario = new Usuario(
                    email,
                    passwordEncoder.encode(password),
                    rol
            );

            repository.save(usuario);
        }
    }
}