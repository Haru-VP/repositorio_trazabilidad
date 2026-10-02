package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadRestControllerTest {

    @Mock
    private ITrazabilidadHandler trazabilidadHandler;

    @InjectMocks
    private TrazabilidadRestController trazabilidadRestController;

    @Test
    void registrarCambioEstado_retornaCreated() {
        // Arrange
        TrazabilidadRequestDto requestDto = new TrazabilidadRequestDto();
        requestDto.setIdPedido(1L);
        TrazabilidadResponseDto responseDto = new TrazabilidadResponseDto();

        when(trazabilidadHandler.registrarCambioEstado(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<TrazabilidadResponseDto> response = trazabilidadRestController.registrarCambioEstado(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(trazabilidadHandler, times(1)).registrarCambioEstado(requestDto);
    }

    @Test
    void obtenerTrazabilidadPorPedido_retornaOkConLista() {
        // Arrange
        Long idPedido = 1L;
        TrazabilidadResponseDto responseDto = new TrazabilidadResponseDto();

        when(trazabilidadHandler.obtenerTrazabilidadPorPedido(idPedido))
                .thenReturn(Collections.singletonList(responseDto));

        // Act
        ResponseEntity<List<TrazabilidadResponseDto>> response =
                trazabilidadRestController.obtenerTrazabilidadPorPedido(idPedido);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(trazabilidadHandler, times(1)).obtenerTrazabilidadPorPedido(idPedido);
    }
}
