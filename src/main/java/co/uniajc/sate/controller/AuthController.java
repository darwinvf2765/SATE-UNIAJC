package co.uniajc.sate.controller;

import co.uniajc.sate.model.Usuario;
import co.uniajc.sate.service.AuthService;
import co.uniajc.sate.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            Usuario usuario = authService.validarCredenciales(
                    request.email(),
                    request.password()
            );

            String token = jwtService.generarToken(usuario);

            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            usuario.getEmail(),
                            usuario.getRol().getNombre()
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Credenciales inválidas"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        try {
            String token = authService.generarTokenRecuperacion(
                    request.email()
            );

            return ResponseEntity.ok(
                    new RecoveryTokenResponse(
                            "Token de recuperación generado",
                            token
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        try {
            authService.restablecerPassword(
                    request.token(),
                    request.nuevaPassword()
            );

            return ResponseEntity.ok(
                    new MessageResponse(
                            "Contraseña restablecida correctamente"
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    public record LoginRequest(
            String email,
            String password
    ) {}

    public record LoginResponse(
            String token,
            String email,
            String rol
    ) {}

    public record ForgotPasswordRequest(
            String email
    ) {}

    public record ResetPasswordRequest(
            String token,
            String nuevaPassword
    ) {}

    public record RecoveryTokenResponse(
            String mensaje,
            String token
    ) {}

    public record MessageResponse(
            String mensaje
    ) {}

    public record ErrorResponse(
            String mensaje
    ) {}
}