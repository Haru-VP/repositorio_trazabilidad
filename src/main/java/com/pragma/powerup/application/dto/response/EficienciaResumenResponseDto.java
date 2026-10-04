package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EficienciaResumenResponseDto {

    private List<PedidoEficienciaResponseDto> pedidos;
    private List<EmpleadoRankingResponseDto> rankingEmpleados;
}
