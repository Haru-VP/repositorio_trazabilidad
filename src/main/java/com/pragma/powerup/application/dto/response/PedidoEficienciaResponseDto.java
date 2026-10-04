package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEficienciaResponseDto {

    private Long idPedido;
    private Long idCliente;
    private String correoCliente;
    private Long idEmpleado;
    private String correoEmpleado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long duracionSegundos;
    private Double duracionMinutos;
    private String duracionFormateada;
    private String estadoFinal;
}
