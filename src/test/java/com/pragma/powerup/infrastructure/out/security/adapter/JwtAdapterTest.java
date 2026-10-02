package com.pragma.powerup.infrastructure.out.security.adapter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtAdapterTest {

    private final String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private JwtAdapter jwtAdapter;

    @BeforeEach
    void setUp() {
        jwtAdapter = new JwtAdapter(secret);
    }

    @Test
    void extraerClaims_tokenValido() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 100L);
        claims.put("rol", "CLIENTE");

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject("cliente@plazoleta.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();

        assertTrue(jwtAdapter.esTokenValido(token));
        assertEquals("cliente@plazoleta.com", jwtAdapter.extraerClaims(token).getSubject());
        assertEquals("CLIENTE", jwtAdapter.extraerClaims(token).get("rol", String.class));
    }

    @Test
    void esTokenValido_tokenInvalido() {
        assertFalse(jwtAdapter.esTokenValido("token_invalido"));
    }
}
