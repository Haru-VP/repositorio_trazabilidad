package com.pragma.powerup.application.dto;

import com.pragma.powerup.application.dto.response.EficienciaResumenResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoRankingResponseDto;
import com.pragma.powerup.application.dto.response.PedidoEficienciaResponseDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class EficienciaDtosTest {

    @Test
    void testPedidoEficienciaResponseDto() {
        LocalDateTime now = LocalDateTime.now();
        PedidoEficienciaResponseDto dto = new PedidoEficienciaResponseDto(
                1L, 2L, "c@p.com", 3L, "e@p.com",
                now, now.plusMinutes(10), 600L, 10.0, "10m 0s", "ENTREGADO"
        );

        assertEquals(1L, dto.getIdPedido());
        assertEquals(2L, dto.getIdCliente());
        assertEquals("c@p.com", dto.getCorreoCliente());
        assertEquals(3L, dto.getIdEmpleado());
        assertEquals("e@p.com", dto.getCorreoEmpleado());
        assertEquals(now, dto.getFechaInicio());
        assertEquals(now.plusMinutes(10), dto.getFechaFin());
        assertEquals(600L, dto.getDuracionSegundos());
        assertEquals(10.0, dto.getDuracionMinutos());
        assertEquals("10m 0s", dto.getDuracionFormateada());
        assertEquals("ENTREGADO", dto.getEstadoFinal());

        PedidoEficienciaResponseDto empty = new PedidoEficienciaResponseDto();
        empty.setIdPedido(5L);
        assertEquals(5L, empty.getIdPedido());
    }

    @Test
    void testEmpleadoRankingResponseDto() {
        EmpleadoRankingResponseDto dto = new EmpleadoRankingResponseDto(
                1, 50L, "emp@p.com", 5, 450.0, 7.5, "7m 30s"
        );

        assertEquals(1, dto.getRanking());
        assertEquals(50L, dto.getIdEmpleado());
        assertEquals("emp@p.com", dto.getCorreoEmpleado());
        assertEquals(5, dto.getCantidadPedidos());
        assertEquals(450.0, dto.getTiempoMedioSegundos());
        assertEquals(7.5, dto.getTiempoMedioMinutos());
        assertEquals("7m 30s", dto.getTiempoMedioFormateado());

        EmpleadoRankingResponseDto empty = new EmpleadoRankingResponseDto();
        empty.setRanking(3);
        assertEquals(3, empty.getRanking());
    }

    @Test
    void testEficienciaResumenResponseDto() {
        List<PedidoEficienciaResponseDto> pedidos = new ArrayList<>();
        List<EmpleadoRankingResponseDto> ranking = new ArrayList<>();
        EficienciaResumenResponseDto dto = new EficienciaResumenResponseDto(pedidos, ranking);

        assertSame(pedidos, dto.getPedidos());
        assertSame(ranking, dto.getRankingEmpleados());

        EficienciaResumenResponseDto empty = new EficienciaResumenResponseDto();
        empty.setPedidos(pedidos);
        empty.setRankingEmpleados(ranking);

        assertSame(pedidos, empty.getPedidos());
        assertSame(ranking, empty.getRankingEmpleados());
    }
}
