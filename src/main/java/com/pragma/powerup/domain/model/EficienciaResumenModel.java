package com.pragma.powerup.domain.model;

import java.util.List;

public class EficienciaResumenModel {

    private List<PedidoEficienciaModel> pedidos;
    private List<EmpleadoRankingModel> rankingEmpleados;

    public EficienciaResumenModel() {
    }

    public EficienciaResumenModel(List<PedidoEficienciaModel> pedidos, List<EmpleadoRankingModel> rankingEmpleados) {
        this.pedidos = pedidos;
        this.rankingEmpleados = rankingEmpleados;
    }

    public List<PedidoEficienciaModel> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoEficienciaModel> pedidos) {
        this.pedidos = pedidos;
    }

    public List<EmpleadoRankingModel> getRankingEmpleados() {
        return rankingEmpleados;
    }

    public void setRankingEmpleados(List<EmpleadoRankingModel> rankingEmpleados) {
        this.rankingEmpleados = rankingEmpleados;
    }
}
