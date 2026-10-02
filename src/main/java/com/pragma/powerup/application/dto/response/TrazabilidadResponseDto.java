package com.pragma.powerup.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de respuesta con el registro de trazabilidad de un pedido")
public class TrazabilidadResponseDto {

    @Schema(description = "Identificador único del registro de auditoría en MongoDB", example = "66fde01b2a9f1a23c4d5e6f7")
    private String id;

    @Schema(description = "Identificador del pedido", example = "1")
    private Long idPedido;

    @Schema(description = "Identificador del cliente", example = "100")
    private Long idCliente;

    @Schema(description = "Correo del cliente", example = "cliente@plazoleta.com")
    private String correoCliente;

    @Schema(description = "Fecha y hora del cambio de estado", example = "2026-10-02T16:00:00")
    private LocalDateTime fecha;

    @Schema(description = "Estado previo del pedido", example = "PENDIENTE")
    private String estadoAnterior;

    @Schema(description = "Nuevo estado del pedido", example = "EN_PREPARACION")
    private String nuevoEstado;

    @Schema(description = "Identificador del empleado asignado", example = "50")
    private Long idEmpleado;

    @Schema(description = "Correo del empleado", example = "empleado@restaurante.com")
    private String correoEmpleado;
}
