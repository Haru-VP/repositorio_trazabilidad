package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.EficienciaResumenResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoRankingResponseDto;
import com.pragma.powerup.application.dto.response.PedidoEficienciaResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import com.pragma.powerup.application.mapper.IEficienciaResponseMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadRequestMapper;
import com.pragma.powerup.application.mapper.ITrazabilidadResponseMapper;
import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrazabilidadHandler implements ITrazabilidadHandler {

    private final ITrazabilidadServicePort trazabilidadServicePort;
    private final ITrazabilidadRequestMapper trazabilidadRequestMapper;
    private final ITrazabilidadResponseMapper trazabilidadResponseMapper;
    private final IEficienciaResponseMapper eficienciaResponseMapper;

    @Override
    public TrazabilidadResponseDto registrarCambioEstado(TrazabilidadRequestDto requestDto) {
        return trazabilidadResponseMapper.toResponseDto(
                trazabilidadServicePort.registrarCambioEstado(
                        trazabilidadRequestMapper.toModel(requestDto)
                )
        );
    }

    @Override
    public List<TrazabilidadResponseDto> obtenerTrazabilidadPorPedido(Long idPedido) {
        return trazabilidadResponseMapper.toResponseDtoList(
                trazabilidadServicePort.obtenerTrazabilidadPorPedido(idPedido)
        );
    }

    @Override
    public List<PedidoEficienciaResponseDto> obtenerEficienciaPedidos() {
        return eficienciaResponseMapper.toPedidoDtoList(
                trazabilidadServicePort.obtenerEficienciaPedidos()
        );
    }

    @Override
    public List<EmpleadoRankingResponseDto> obtenerRankingEficienciaEmpleados() {
        return eficienciaResponseMapper.toRankingDtoList(
                trazabilidadServicePort.obtenerRankingEficienciaEmpleados()
        );
    }

    @Override
    public EficienciaResumenResponseDto obtenerResumenEficiencia() {
        return eficienciaResponseMapper.toResumenDto(
                trazabilidadServicePort.obtenerResumenEficiencia()
        );
    }
}
