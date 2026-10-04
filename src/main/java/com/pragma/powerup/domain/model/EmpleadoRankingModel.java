package com.pragma.powerup.domain.model;

public class EmpleadoRankingModel {

    private Integer ranking;
    private Long idEmpleado;
    private String correoEmpleado;
    private Integer cantidadPedidos;
    private Double tiempoMedioSegundos;
    private Double tiempoMedioMinutos;
    private String tiempoMedioFormateado;

    public EmpleadoRankingModel() {
    }

    public EmpleadoRankingModel(Integer ranking, Long idEmpleado, String correoEmpleado,
                                Integer cantidadPedidos, Double tiempoMedioSegundos,
                                Double tiempoMedioMinutos, String tiempoMedioFormateado) {
        this.ranking = ranking;
        this.idEmpleado = idEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.cantidadPedidos = cantidadPedidos;
        this.tiempoMedioSegundos = tiempoMedioSegundos;
        this.tiempoMedioMinutos = tiempoMedioMinutos;
        this.tiempoMedioFormateado = tiempoMedioFormateado;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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

    public Integer getCantidadPedidos() {
        return cantidadPedidos;
    }

    public void setCantidadPedidos(Integer cantidadPedidos) {
        this.cantidadPedidos = cantidadPedidos;
    }

    public Double getTiempoMedioSegundos() {
        return tiempoMedioSegundos;
    }

    public void setTiempoMedioSegundos(Double tiempoMedioSegundos) {
        this.tiempoMedioSegundos = tiempoMedioSegundos;
    }

    public Double getTiempoMedioMinutos() {
        return tiempoMedioMinutos;
    }

    public void setTiempoMedioMinutos(Double tiempoMedioMinutos) {
        this.tiempoMedioMinutos = tiempoMedioMinutos;
    }

    public String getTiempoMedioFormateado() {
        return tiempoMedioFormateado;
    }

    public void setTiempoMedioFormateado(String tiempoMedioFormateado) {
        this.tiempoMedioFormateado = tiempoMedioFormateado;
    }
}
