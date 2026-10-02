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
}
