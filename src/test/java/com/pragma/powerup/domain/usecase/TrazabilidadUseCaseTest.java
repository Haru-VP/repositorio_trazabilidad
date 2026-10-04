package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.ParametrosInvalidosException;
import com.pragma.powerup.domain.exception.PedidoNoEncontradoException;
import com.pragma.powerup.domain.exception.UsuarioNoAutorizadoException;
import com.pragma.powerup.domain.model.TrazabilidadModel;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadUseCaseTest {

    @Mock
    private ITrazabilidadPersistencePort trazabilidadPersistencePort;

    @Mock
    private IAuthenticatedUserPort authenticatedUserPort;

    @InjectMocks
    private TrazabilidadUseCase trazabilidadUseCase;

    @Test
    void registrarCambioEstado_exitoso() {
        // Arrange
        TrazabilidadModel model = new TrazabilidadModel(
                null, 1L, 100L, "cliente@plazoleta.com", null,
                null, "PENDIENTE", null, null
        );
        TrazabilidadModel savedModel = new TrazabilidadModel(
                "id1", 1L, 100L, "cliente@plazoleta.com", LocalDateTime.now(),
                null, "PENDIENTE", null, null
        );

        when(trazabilidadPersistencePort.guardarTrazabilidad(any(TrazabilidadModel.class))).thenReturn(savedModel);

        // Act
        TrazabilidadModel resultado = trazabilidadUseCase.registrarCambioEstado(model);

        // Assert
        assertNotNull(resultado);
        assertNotNull(model.getFecha());
        assertEquals("id1", resultado.getId());
        verify(trazabilidadPersistencePort, times(1)).guardarTrazabilidad(model);
    }

    @Test
    void registrarCambioEstado_falla_modeloNulo() {
        assertThrows(ParametrosInvalidosException.class, () ->
                trazabilidadUseCase.registrarCambioEstado(null)
        );
        verify(trazabilidadPersistencePort, never()).guardarTrazabilidad(any());
    }

    @Test
    void registrarCambioEstado_falla_idPedidoNulo() {
        TrazabilidadModel model = new TrazabilidadModel();
        model.setIdPedido(null);

        assertThrows(ParametrosInvalidosException.class, () ->
                trazabilidadUseCase.registrarCambioEstado(model)
        );
        verify(trazabilidadPersistencePort, never()).guardarTrazabilidad(any());
    }

    @Test
    void obtenerTrazabilidadPorPedido_exitoso_ordenadoPorFecha() {
        // Arrange
        Long idPedido = 1L;
        Long idCliente = 100L;
        LocalDateTime ahora = LocalDateTime.now();

        TrazabilidadModel t1 = new TrazabilidadModel("1", idPedido, idCliente, "c@p.com", ahora.minusMinutes(10), null, "PENDIENTE", null, null);
        TrazabilidadModel t2 = new TrazabilidadModel("2", idPedido, idCliente, "c@p.com", ahora.minusMinutes(5), "PENDIENTE", "EN_PREPARACION", 50L, "e@p.com");
        TrazabilidadModel t3 = new TrazabilidadModel("3", idPedido, idCliente, "c@p.com", ahora, "EN_PREPARACION", "LISTO", 50L, "e@p.com");

        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(idCliente);
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("CLIENTE");
        when(trazabilidadPersistencePort.obtenerTrazabilidadPorPedido(idPedido))
                .thenReturn(Arrays.asList(t2, t1, t3)); // Desordenado para verificar ordenamiento

        // Act
        List<TrazabilidadModel> resultado = trazabilidadUseCase.obtenerTrazabilidadPorPedido(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getNuevoEstado());
        assertEquals("EN_PREPARACION", resultado.get(1).getNuevoEstado());
        assertEquals("LISTO", resultado.get(2).getNuevoEstado());
    }

    @Test
    void obtenerTrazabilidadPorPedido_falla_idPedidoInvalido() {
        assertThrows(ParametrosInvalidosException.class, () ->
                trazabilidadUseCase.obtenerTrazabilidadPorPedido(null)
        );
        assertThrows(ParametrosInvalidosException.class, () ->
                trazabilidadUseCase.obtenerTrazabilidadPorPedido(0L)
        );
        assertThrows(ParametrosInvalidosException.class, () ->
                trazabilidadUseCase.obtenerTrazabilidadPorPedido(-1L)
        );
        verify(trazabilidadPersistencePort, never()).obtenerTrazabilidadPorPedido(any());
    }

    @Test
    void obtenerTrazabilidadPorPedido_falla_usuarioNoAutenticado() {
        Long idPedido = 1L;
        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(null);

        assertThrows(UsuarioNoAutorizadoException.class, () ->
                trazabilidadUseCase.obtenerTrazabilidadPorPedido(idPedido)
        );
        verify(trazabilidadPersistencePort, never()).obtenerTrazabilidadPorPedido(any());
    }

    @Test
    void obtenerTrazabilidadPorPedido_falla_pedidoNoEncontrado() {
        Long idPedido = 1L;
        Long idCliente = 100L;

        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(idCliente);
        when(trazabilidadPersistencePort.obtenerTrazabilidadPorPedido(idPedido)).thenReturn(Collections.emptyList());

        assertThrows(PedidoNoEncontradoException.class, () ->
                trazabilidadUseCase.obtenerTrazabilidadPorPedido(idPedido)
        );
    }

    @Test
    void obtenerTrazabilidadPorPedido_falla_clienteNoEsDuenioDelPedido() {
        Long idPedido = 1L;
        Long idClienteAutenticado = 100L;
        Long idClienteDuenio = 200L;

        TrazabilidadModel log = new TrazabilidadModel("1", idPedido, idClienteDuenio, "otro@p.com", LocalDateTime.now(), null, "PENDIENTE", null, null);

        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(idClienteAutenticado);
        when(trazabilidadPersistencePort.obtenerTrazabilidadPorPedido(idPedido)).thenReturn(Collections.singletonList(log));
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("CLIENTE");

        assertThrows(UsuarioNoAutorizadoException.class, () ->
                trazabilidadUseCase.obtenerTrazabilidadPorPedido(idPedido)
        );
    }

    @Test
    void obtenerEficienciaPedidos_exitoso() {
        LocalDateTime t0 = LocalDateTime.of(2026, 10, 1, 12, 0, 0);
        LocalDateTime t1 = LocalDateTime.of(2026, 10, 1, 12, 15, 30); // 15m 30s = 930s

        TrazabilidadModel logInicio = new TrazabilidadModel("1", 10L, 100L, "c1@p.com", t0, null, "PENDIENTE", null, null);
        TrazabilidadModel logPrep = new TrazabilidadModel("2", 10L, 100L, "c1@p.com", t0.plusMinutes(5), "PENDIENTE", "EN_PREPARACION", 50L, "emp1@p.com");
        TrazabilidadModel logEntrega = new TrazabilidadModel("3", 10L, 100L, "c1@p.com", t1, "LISTO", "ENTREGADO", 50L, "emp1@p.com");

        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(1L);
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("PROPIETARIO");
        when(trazabilidadPersistencePort.obtenerTodosLosRegistros()).thenReturn(Arrays.asList(logInicio, logPrep, logEntrega));

        List<com.pragma.powerup.domain.model.PedidoEficienciaModel> resultado = trazabilidadUseCase.obtenerEficienciaPedidos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        com.pragma.powerup.domain.model.PedidoEficienciaModel p = resultado.get(0);
        assertEquals(10L, p.getIdPedido());
        assertEquals(100L, p.getIdCliente());
        assertEquals(50L, p.getIdEmpleado());
        assertEquals(930L, p.getDuracionSegundos());
        assertEquals(15.5, p.getDuracionMinutos());
        assertEquals("15m 30s", p.getDuracionFormateada());
        assertEquals("ENTREGADO", p.getEstadoFinal());
    }

    @Test
    void obtenerEficienciaPedidos_vacio_sinRegistros() {
        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(1L);
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("PROPIETARIO");
        when(trazabilidadPersistencePort.obtenerTodosLosRegistros()).thenReturn(Collections.emptyList());

        List<com.pragma.powerup.domain.model.PedidoEficienciaModel> resultado = trazabilidadUseCase.obtenerEficienciaPedidos();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void obtenerEficienciaPedidos_falla_usuarioNoAutenticado() {
        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(null);

        assertThrows(UsuarioNoAutorizadoException.class, () ->
                trazabilidadUseCase.obtenerEficienciaPedidos()
        );
    }

    @Test
    void obtenerEficienciaPedidos_falla_rolNoAutorizado() {
        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(2L);
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("CLIENTE");

        assertThrows(UsuarioNoAutorizadoException.class, () ->
                trazabilidadUseCase.obtenerEficienciaPedidos()
        );
    }

    @Test
    void obtenerRankingEficienciaEmpleados_exitoso() {
        LocalDateTime t0 = LocalDateTime.of(2026, 10, 1, 10, 0, 0);

        // Pedido 1: Empleado 50, tarda 10 minutos (600s)
        TrazabilidadModel p1Inicio = new TrazabilidadModel("1", 1L, 100L, "c1@p.com", t0, null, "PENDIENTE", null, null);
        TrazabilidadModel p1Fin = new TrazabilidadModel("2", 1L, 100L, "c1@p.com", t0.plusMinutes(10), "LISTO", "ENTREGADO", 50L, "e50@p.com");

        // Pedido 2: Empleado 60, tarda 5 minutos (300s) -> Mejor ranking!
        TrazabilidadModel p2Inicio = new TrazabilidadModel("3", 2L, 101L, "c2@p.com", t0, null, "PENDIENTE", null, null);
        TrazabilidadModel p2Fin = new TrazabilidadModel("4", 2L, 101L, "c2@p.com", t0.plusMinutes(5), "LISTO", "ENTREGADO", 60L, "e60@p.com");

        // Pedido 3: Empleado 50, tarda 20 minutos (1200s) -> Media empleado 50: (600 + 1200)/2 = 900s (15 min)
        TrazabilidadModel p3Inicio = new TrazabilidadModel("5", 3L, 102L, "c3@p.com", t0, null, "PENDIENTE", null, null);
        TrazabilidadModel p3Fin = new TrazabilidadModel("6", 3L, 102L, "c3@p.com", t0.plusMinutes(20), "LISTO", "ENTREGADO", 50L, "e50@p.com");

        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(1L);
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("ROLE_PROPIETARIO");
        when(trazabilidadPersistencePort.obtenerTodosLosRegistros())
                .thenReturn(Arrays.asList(p1Inicio, p1Fin, p2Inicio, p2Fin, p3Inicio, p3Fin));

        List<com.pragma.powerup.domain.model.EmpleadoRankingModel> ranking = trazabilidadUseCase.obtenerRankingEficienciaEmpleados();

        assertNotNull(ranking);
        assertEquals(2, ranking.size());

        // Rank 1: Empleado 60 (300s = 5m)
        assertEquals(1, ranking.get(0).getRanking());
        assertEquals(60L, ranking.get(0).getIdEmpleado());
        assertEquals(1, ranking.get(0).getCantidadPedidos());
        assertEquals(300.0, ranking.get(0).getTiempoMedioSegundos());
        assertEquals(5.0, ranking.get(0).getTiempoMedioMinutos());

        // Rank 2: Empleado 50 (900s = 15m)
        assertEquals(2, ranking.get(1).getRanking());
        assertEquals(50L, ranking.get(1).getIdEmpleado());
        assertEquals(2, ranking.get(1).getCantidadPedidos());
        assertEquals(900.0, ranking.get(1).getTiempoMedioSegundos());
        assertEquals(15.0, ranking.get(1).getTiempoMedioMinutos());
    }

    @Test
    void obtenerResumenEficiencia_exitoso() {
        when(authenticatedUserPort.obtenerIdUsuarioAutenticado()).thenReturn(1L);
        when(authenticatedUserPort.obtenerRolUsuarioAutenticado()).thenReturn("ADMINISTRADOR");
        when(trazabilidadPersistencePort.obtenerTodosLosRegistros()).thenReturn(Collections.emptyList());

        com.pragma.powerup.domain.model.EficienciaResumenModel resumen = trazabilidadUseCase.obtenerResumenEficiencia();

        assertNotNull(resumen);
        assertNotNull(resumen.getPedidos());
        assertNotNull(resumen.getRankingEmpleados());
    }
}
