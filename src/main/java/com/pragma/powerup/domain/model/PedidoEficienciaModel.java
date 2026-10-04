package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;

public class PedidoEficienciaModel {

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

    public PedidoEficienciaModel() {
    }

    public PedidoEficienciaModel(Long idPedido, Long idCliente, String correoCliente,
                                 Long idEmpleado, String correoEmpleado,
                                 LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                 Long duracionSegundos, Double duracionMinutos,
                                 String duracionFormateada, String estadoFinal) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.correoCliente = correoCliente;
        this.idEmpleado = idEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionSegundos = duracionSegundos;
        this.duracionMinutos = duracionMinutos;
        this.duracionFormateada = duracionFormateada;
        this.estadoFinal = estadoFinal;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(Long duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public Double getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Double duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDuracionFormateada() {
        return duracionFormateada;
    }

    public void setDuracionFormateada(String duracionFormateada) {
        this.duracionFormateada = duracionFormateada;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }
}
