package com.pragma.powerup.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para registrar un cambio de estado en la trazabilidad del pedido")
public class TrazabilidadRequestDto {

    @NotNull(message = "El id del pedido es obligatorio")
    @Schema(description = "Identificador del pedido", example = "1")
    private Long idPedido;

    @NotNull(message = "El id del cliente es obligatorio")
    @Schema(description = "Identificador del cliente", example = "100")
    private Long idCliente;

    @Schema(description = "Correo electrónico del cliente", example = "cliente@plazoleta.com")
    private String correoCliente;

    @Schema(description = "Fecha y hora del cambio de estado", example = "2026-10-02T16:00:00")
    private LocalDateTime fecha;

    @Schema(description = "Estado previo del pedido", example = "PENDIENTE")
    private String estadoAnterior;

    @NotNull(message = "El nuevo estado es obligatorio")
    @Schema(description = "Nuevo estado del pedido", example = "EN_PREPARACION")
    private String nuevoEstado;

    @Schema(description = "Identificador del empleado asignado", example = "50")
    private Long idEmpleado;

    @Schema(description = "Correo del empleado", example = "empleado@restaurante.com")
    private String correoEmpleado;
}
