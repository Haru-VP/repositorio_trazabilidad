package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EficienciaModelsTest {

    @Test
    void testPedidoEficienciaModel() {
        LocalDateTime now = LocalDateTime.now();
        PedidoEficienciaModel model = new PedidoEficienciaModel(
                1L, 2L, "cliente@p.com", 3L, "empleado@p.com",
                now, now.plusMinutes(10), 600L, 10.0, "10m 0s", "ENTREGADO"
        );

        assertEquals(1L, model.getIdPedido());
        assertEquals(2L, model.getIdCliente());
        assertEquals("cliente@p.com", model.getCorreoCliente());
        assertEquals(3L, model.getIdEmpleado());
        assertEquals("empleado@p.com", model.getCorreoEmpleado());
        assertEquals(now, model.getFechaInicio());
        assertEquals(now.plusMinutes(10), model.getFechaFin());
        assertEquals(600L, model.getDuracionSegundos());
        assertEquals(10.0, model.getDuracionMinutos());
        assertEquals("10m 0s", model.getDuracionFormateada());
        assertEquals("ENTREGADO", model.getEstadoFinal());

        PedidoEficienciaModel empty = new PedidoEficienciaModel();
        empty.setIdPedido(2L);
        empty.setIdCliente(3L);
        empty.setCorreoCliente("c2@p.com");
        empty.setIdEmpleado(4L);
        empty.setCorreoEmpleado("e2@p.com");
        empty.setFechaInicio(now);
        empty.setFechaFin(now.plusMinutes(5));
        empty.setDuracionSegundos(300L);
        empty.setDuracionMinutos(5.0);
        empty.setDuracionFormateada("5m 0s");
        empty.setEstadoFinal("ENTREGADO");

        assertEquals(2L, empty.getIdPedido());
        assertEquals("c2@p.com", empty.getCorreoCliente());
    }

    @Test
    void testEmpleadoRankingModel() {
        EmpleadoRankingModel model = new EmpleadoRankingModel(
                1, 50L, "emp@p.com", 5, 450.0, 7.5, "7m 30s"
        );

        assertEquals(1, model.getRanking());
        assertEquals(50L, model.getIdEmpleado());
        assertEquals("emp@p.com", model.getCorreoEmpleado());
        assertEquals(5, model.getCantidadPedidos());
        assertEquals(450.0, model.getTiempoMedioSegundos());
        assertEquals(7.5, model.getTiempoMedioMinutos());
        assertEquals("7m 30s", model.getTiempoMedioFormateado());

        EmpleadoRankingModel empty = new EmpleadoRankingModel();
        empty.setRanking(2);
        empty.setIdEmpleado(60L);
        empty.setCorreoEmpleado("emp2@p.com");
        empty.setCantidadPedidos(2);
        empty.setTiempoMedioSegundos(600.0);
        empty.setTiempoMedioMinutos(10.0);
        empty.setTiempoMedioFormateado("10m 0s");

        assertEquals(2, empty.getRanking());
        assertEquals(60L, empty.getIdEmpleado());
    }

    @Test
    void testEficienciaResumenModel() {
        List<PedidoEficienciaModel> pedidos = new ArrayList<>();
        List<EmpleadoRankingModel> ranking = new ArrayList<>();
        EficienciaResumenModel resumen = new EficienciaResumenModel(pedidos, ranking);

        assertSame(pedidos, resumen.getPedidos());
        assertSame(ranking, resumen.getRankingEmpleados());

        EficienciaResumenModel empty = new EficienciaResumenModel();
        empty.setPedidos(pedidos);
        empty.setRankingEmpleados(ranking);

        assertSame(pedidos, empty.getPedidos());
        assertSame(ranking, empty.getRankingEmpleados());
    }
}
