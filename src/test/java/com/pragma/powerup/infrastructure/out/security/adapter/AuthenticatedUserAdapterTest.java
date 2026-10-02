package com.pragma.powerup.infrastructure.out.security.adapter;

import com.pragma.powerup.infrastructure.out.security.model.AuthenticatedUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthenticatedUserAdapterTest {

    private final AuthenticatedUserAdapter adapter = new AuthenticatedUserAdapter();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void obtenerDatosUsuarioAutenticado_conContextoValido() {
        AuthenticatedUser user = new AuthenticatedUser(100L, "cliente@plazoleta.com", "CLIENTE");
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertEquals(100L, adapter.obtenerIdUsuarioAutenticado());
        assertEquals("CLIENTE", adapter.obtenerRolUsuarioAutenticado());
        assertEquals("cliente@plazoleta.com", adapter.obtenerCorreoUsuarioAutenticado());
    }

    @Test
    void obtenerDatosUsuarioAutenticado_sinContexto() {
        SecurityContextHolder.clearContext();

        assertNull(adapter.obtenerIdUsuarioAutenticado());
        assertNull(adapter.obtenerRolUsuarioAutenticado());
        assertNull(adapter.obtenerCorreoUsuarioAutenticado());
    }
}
