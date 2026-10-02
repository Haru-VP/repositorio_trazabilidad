package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.ParametrosInvalidosException;
import com.pragma.powerup.domain.exception.PedidoNoEncontradoException;
import com.pragma.powerup.domain.exception.UsuarioNoAutorizadoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ControllerAdvisorTest {

    private final ControllerAdvisor advisor = new ControllerAdvisor();

    @Test
    void handlePedidoNoEncontradoException() {
        ResponseEntity<Map<String, String>> response =
                advisor.handlePedidoNoEncontradoException(new PedidoNoEncontradoException());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.PEDIDO_NO_ENCONTRADO.getMessage(), response.getBody().get("message"));
    }

    @Test
    void handleUsuarioNoAutorizadoException() {
        ResponseEntity<Map<String, String>> response =
                advisor.handleUsuarioNoAutorizadoException(new UsuarioNoAutorizadoException());

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.USUARIO_NO_AUTORIZADO.getMessage(), response.getBody().get("message"));
    }

    @Test
    void handleParametrosInvalidosException() {
        ResponseEntity<Map<String, String>> response =
                advisor.handleParametrosInvalidosException(new ParametrosInvalidosException());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExceptionResponse.PARAMETROS_INVALIDOS.getMessage(), response.getBody().get("message"));
    }
}
