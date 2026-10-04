package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.TrazabilidadRequestDto;
import com.pragma.powerup.application.dto.response.EficienciaResumenResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoRankingResponseDto;
import com.pragma.powerup.application.dto.response.PedidoEficienciaResponseDto;
import com.pragma.powerup.application.dto.response.TrazabilidadResponseDto;
import com.pragma.powerup.application.handler.ITrazabilidadHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Trazabilidad", description = "Endpoints para la auditoría y consulta de trazabilidad de los pedidos")
@RestController
@RequestMapping("/api/v1/trazabilidad")
@RequiredArgsConstructor
public class TrazabilidadRestController {

    private final ITrazabilidadHandler trazabilidadHandler;

    @Operation(summary = "Registrar un cambio de estado en la trazabilidad de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cambio de estado registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TrazabilidadResponseDto> registrarCambioEstado(
            @Valid @RequestBody TrazabilidadRequestDto requestDto) {
        TrazabilidadResponseDto responseDto = trazabilidadHandler.registrarCambioEstado(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar el historial de trazabilidad de un pedido (Solo el Cliente dueño del pedido)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de trazabilidad obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Identificador de pedido inválido", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: solo el cliente dueño puede consultar este pedido", content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontraron registros para el pedido", content = @Content)
    })
    @GetMapping(value = {"/pedido/{idPedido}", "/{idPedido}"})
    public ResponseEntity<List<TrazabilidadResponseDto>> obtenerTrazabilidadPorPedido(
            @PathVariable Long idPedido) {
        return ResponseEntity.ok(trazabilidadHandler.obtenerTrazabilidadPorPedido(idPedido));
    }

    @Operation(summary = "Consultar el tiempo transcurrido entre inicio y fin de cada pedido (Solo Propietarios)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eficiencia por pedido obtenida exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: solo usuarios con rol PROPIETARIO pueden consultar la eficiencia", content = @Content)
    })
    @GetMapping("/eficiencia/pedidos")
    public ResponseEntity<List<PedidoEficienciaResponseDto>> obtenerEficienciaPedidos() {
        return ResponseEntity.ok(trazabilidadHandler.obtenerEficienciaPedidos());
    }

    @Operation(summary = "Consultar el ranking de empleados por tiempo medio de pedidos (Solo Propietarios)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking de empleados obtenido exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: solo usuarios con rol PROPIETARIO pueden consultar el ranking", content = @Content)
    })
    @GetMapping("/eficiencia/ranking")
    public ResponseEntity<List<EmpleadoRankingResponseDto>> obtenerRankingEficienciaEmpleados() {
        return ResponseEntity.ok(trazabilidadHandler.obtenerRankingEficienciaEmpleados());
    }

    @Operation(summary = "Consultar el resumen consolidado de eficiencia: pedidos y ranking de empleados (Solo Propietarios)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumen de eficiencia obtenido exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: solo usuarios con rol PROPIETARIO pueden consultar la eficiencia", content = @Content)
    })
    @GetMapping("/eficiencia")
    public ResponseEntity<EficienciaResumenResponseDto> obtenerResumenEficiencia() {
        return ResponseEntity.ok(trazabilidadHandler.obtenerResumenEficiencia());
    }
}
