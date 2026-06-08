package com.backend.java.saude_e_bem_estar.security;

import com.backend.java.saude_e_bem_estar.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;
    private final String secret = "test-secret-key-32-chars-long-minimum-to-be-secure-1234";

    @BeforeEach
    void setUp() {
        tokenService = new TokenService(secret);
    }

    @Test
    void shouldGenerateValidTokenForUser() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setNome_completo("Test User");

        String token = tokenService.generateToken(usuario);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void shouldValidateTokenAndReturnEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setNome_completo("Test User");

        String token = tokenService.generateToken(usuario);
        String subject = tokenService.validateToken(token);

        assertEquals("test@example.com", subject);
    }

    @Test
    void shouldReturnEmptyStringWhenTokenIsInvalid() {
        String invalidToken = "invalid-token-string";
        String subject = tokenService.validateToken(invalidToken);

        assertEquals("", subject);
    }

    @Test
    void shouldReturnEmptyStringWhenTokenIsNull() {
        String subject = tokenService.validateToken(null);

        assertEquals("", subject);
    }
}
