package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrazabilidadModelTest {

    @Test
    void testGettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();
        TrazabilidadModel model = new TrazabilidadModel();
        model.setId("id1");
        model.setIdPedido(1L);
        model.setIdCliente(100L);
        model.setCorreoCliente("c@p.com");
        model.setFecha(now);
        model.setEstadoAnterior("PENDIENTE");
        model.setNuevoEstado("EN_PREPARACION");
        model.setIdEmpleado(50L);
        model.setCorreoEmpleado("e@p.com");

        assertEquals("id1", model.getId());
        assertEquals(1L, model.getIdPedido());
        assertEquals(100L, model.getIdCliente());
        assertEquals("c@p.com", model.getCorreoCliente());
        assertEquals(now, model.getFecha());
        assertEquals("PENDIENTE", model.getEstadoAnterior());
        assertEquals("EN_PREPARACION", model.getNuevoEstado());
        assertEquals(50L, model.getIdEmpleado());
        assertEquals("e@p.com", model.getCorreoEmpleado());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        TrazabilidadModel model = new TrazabilidadModel(
                "id1", 1L, 100L, "c@p.com", now, "PENDIENTE", "EN_PREPARACION", 50L, "e@p.com"
        );
        assertNotNull(model);
        assertEquals("id1", model.getId());
        assertEquals(1L, model.getIdPedido());
    }
}
