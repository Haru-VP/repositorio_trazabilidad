package com.pragma.powerup.infrastructure.out.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "trazabilidad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrazabilidadDocument {

    @Id
    private String id;

    @Field("id_pedido")
    private Long idPedido;

    @Field("id_cliente")
    private Long idCliente;

    @Field("correo_cliente")
    private String correoCliente;

    @Field("fecha")
    private LocalDateTime fecha;

    @Field("estado_anterior")
    private String estadoAnterior;

    @Field("nuevo_estado")
    private String nuevoEstado;

    @Field("id_empleado")
    private Long idEmpleado;

    @Field("correo_empleado")
    private String correoEmpleado;
}
