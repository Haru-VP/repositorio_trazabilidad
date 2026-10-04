package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.EficienciaResumenResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoRankingResponseDto;
import com.pragma.powerup.application.dto.response.PedidoEficienciaResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;

import java.util.List;

public interface ITrazabilidadHandler {

    TrazabilidadResponseDto registrarCambioEstado(TrazabilidadRequestDto requestDto);

    List<TrazabilidadResponseDto> obtenerTrazabilidadPorPedido(Long idPedido);

    List<PedidoEficienciaResponseDto> obtenerEficienciaPedidos();

    List<EmpleadoRankingResponseDto> obtenerRankingEficienciaEmpleados();

    EficienciaResumenResponseDto obtenerResumenEficiencia();
}
