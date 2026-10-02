package com.pragma.powerup.infrastructure.out.mongodb.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrazabilidadDocumentTest {

    @Test
    void testGettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();
        TrazabilidadDocument doc = new TrazabilidadDocument();
        doc.setId("id1");
        doc.setIdPedido(1L);
        doc.setIdCliente(100L);
        doc.setCorreoCliente("c@p.com");
        doc.setFecha(now);
        doc.setEstadoAnterior("PENDIENTE");
        doc.setNuevoEstado("EN_PREPARACION");
        doc.setIdEmpleado(50L);
        doc.setCorreoEmpleado("e@p.com");

        assertEquals("id1", doc.getId());
        assertEquals(1L, doc.getIdPedido());
        assertEquals(100L, doc.getIdCliente());
        assertEquals("c@p.com", doc.getCorreoCliente());
        assertEquals(now, doc.getFecha());
        assertEquals("PENDIENTE", doc.getEstadoAnterior());
        assertEquals("EN_PREPARACION", doc.getNuevoEstado());
        assertEquals(50L, doc.getIdEmpleado());
        assertEquals("e@p.com", doc.getCorreoEmpleado());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        TrazabilidadDocument doc = new TrazabilidadDocument(
                "id1", 1L, 100L, "c@p.com", now, "PENDIENTE", "EN_PREPARACION", 50L, "e@p.com"
        );
        assertNotNull(doc);
        assertEquals("id1", doc.getId());
    }
}
