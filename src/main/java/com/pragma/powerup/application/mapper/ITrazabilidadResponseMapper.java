package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.domain.model.TrazabilidadModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITrazabilidadResponseMapper {

    TrazabilidadResponseDto toResponseDto(TrazabilidadModel model);

    List<TrazabilidadResponseDto> toResponseDtoList(List<TrazabilidadModel> modelList);
}
