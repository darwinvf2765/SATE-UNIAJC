package co.uniajc.sate.service;

import co.uniajc.sate.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "SATE-UNIAJC-clave-secreta-para-JWT-2026-segura";

    private static final long EXPIRATION_TIME =
            1000L * 60 * 60;

    private final SecretKey key;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generarToken(Usuario usuario) {

        Date ahora = new Date();
        Date expiracion = new Date(
                ahora.getTime() + EXPIRATION_TIME
        );

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("rol", usuario.getRol().getNombre())
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(key)
                .compact();
    }

    public String extraerEmail(String token) {

        return obtenerClaims(token)
                .getSubject();
    }

    public String extraerRol(String token) {

        return obtenerClaims(token)
                .get("rol", String.class);
    }

    private Claims obtenerClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
