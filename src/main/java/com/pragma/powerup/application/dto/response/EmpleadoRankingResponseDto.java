package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoRankingResponseDto {

    private Integer ranking;
    private Long idEmpleado;
    private String correoEmpleado;
    private Integer cantidadPedidos;
    private Double tiempoMedioSegundos;
    private Double tiempoMedioMinutos;
    private String tiempoMedioFormateado;
}
