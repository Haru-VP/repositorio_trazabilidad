package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.EficienciaResumenResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoRankingResponseDto;
import com.pragma.powerup.application.dto.response.PedidoEficienciaResponseDto;
import com.pragma.powerup.domain.model.EficienciaResumenModel;
import com.pragma.powerup.domain.model.EmpleadoRankingModel;
import com.pragma.powerup.domain.model.PedidoEficienciaModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEficienciaResponseMapper {

    PedidoEficienciaResponseDto toPedidoDto(PedidoEficienciaModel model);

    List<PedidoEficienciaResponseDto> toPedidoDtoList(List<PedidoEficienciaModel> modelList);

    EmpleadoRankingResponseDto toRankingDto(EmpleadoRankingModel model);

    List<EmpleadoRankingResponseDto> toRankingDtoList(List<EmpleadoRankingModel> modelList);

    EficienciaResumenResponseDto toResumenDto(EficienciaResumenModel model);
}
