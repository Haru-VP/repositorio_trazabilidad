package com.pragma.powerup.application.dto;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrazabilidadDtoTest {

    @Test
    void testRequestDto() {
        LocalDateTime now = LocalDateTime.now();
        TrazabilidadRequestDto dto = new TrazabilidadRequestDto(
                1L, 100L, "c@p.com", now, "PENDIENTE", "EN_PREPARACION", 50L, "e@p.com"
        );

        assertEquals(1L, dto.getIdPedido());
        assertEquals(100L, dto.getIdCliente());
        assertEquals("c@p.com", dto.getCorreoCliente());
        assertEquals(now, dto.getFecha());
        assertEquals("PENDIENTE", dto.getEstadoAnterior());
        assertEquals("EN_PREPARACION", dto.getNuevoEstado());
        assertEquals(50L, dto.getIdEmpleado());
        assertEquals("e@p.com", dto.getCorreoEmpleado());

        TrazabilidadRequestDto empty = new TrazabilidadRequestDto();
        empty.setIdPedido(2L);
        assertEquals(2L, empty.getIdPedido());
    }

    @Test
    void testResponseDto() {
        LocalDateTime now = LocalDateTime.now();
        TrazabilidadResponseDto dto = new TrazabilidadResponseDto(
                "id1", 1L, 100L, "c@p.com", now, "PENDIENTE", "EN_PREPARACION", 50L, "e@p.com"
        );

        assertNotNull(dto);
        assertEquals("id1", dto.getId());
        assertEquals(1L, dto.getIdPedido());

        TrazabilidadResponseDto empty = new TrazabilidadResponseDto();
        empty.setId("id2");
        assertEquals("id2", empty.getId());
    }
}
