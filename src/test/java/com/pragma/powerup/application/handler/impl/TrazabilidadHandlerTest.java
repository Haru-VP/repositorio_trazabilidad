package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.mapper.ITrazabilidadRequestMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadResponseMapper;
import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.model.TrazabilidadModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadHandlerTest {

    @Mock
    private ITrazabilidadServicePort trazabilidadServicePort;

    @Mock
    private ITrazabilidadRequestMapper trazabilidadRequestMapper;

    @Mock
    private ITrazabilidadResponseMapper trazabilidadResponseMapper;

    @InjectMocks
    private TrazabilidadHandler trazabilidadHandler;

    @Test
    void registrarCambioEstado_exitoso() {
        // Arrange
        TrazabilidadRequestDto requestDto = new TrazabilidadRequestDto();
        requestDto.setIdPedido(1L);
        TrazabilidadModel model = new TrazabilidadModel();
        TrazabilidadResponseDto responseDto = new TrazabilidadResponseDto();

        when(trazabilidadRequestMapper.toModel(requestDto)).thenReturn(model);
        when(trazabilidadServicePort.registrarCambioEstado(model)).thenReturn(model);
        when(trazabilidadResponseMapper.toResponseDto(model)).thenReturn(responseDto);

        // Act
        TrazabilidadResponseDto resultado = trazabilidadHandler.registrarCambioEstado(requestDto);

        // Assert
        assertNotNull(resultado);
        verify(trazabilidadRequestMapper, times(1)).toModel(requestDto);
        verify(trazabilidadServicePort, times(1)).registrarCambioEstado(model);
        verify(trazabilidadResponseMapper, times(1)).toResponseDto(model);
    }

    @Test
    void obtenerTrazabilidadPorPedido_exitoso() {
        // Arrange
        Long idPedido = 1L;
        TrazabilidadModel model = new TrazabilidadModel();
        TrazabilidadResponseDto responseDto = new TrazabilidadResponseDto();

        when(trazabilidadServicePort.obtenerTrazabilidadPorPedido(idPedido))
                .thenReturn(Collections.singletonList(model));
        when(trazabilidadResponseMapper.toResponseDtoList(Collections.singletonList(model)))
                .thenReturn(Collections.singletonList(responseDto));

        // Act
        List<TrazabilidadResponseDto> resultado = trazabilidadHandler.obtenerTrazabilidadPorPedido(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(trazabilidadServicePort, times(1)).obtenerTrazabilidadPorPedido(idPedido);
        verify(trazabilidadResponseMapper, times(1)).toResponseDtoList(Collections.singletonList(model));
    }
}
