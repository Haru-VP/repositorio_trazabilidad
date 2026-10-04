package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.EficienciaResumenModel;
import com.pragma.powerup.domain.model.EmpleadoRankingModel;
import com.pragma.powerup.domain.model.PedidoEficienciaModel;
import com.pragma.powerup.domain.model.TrazabilidadModel;

import java.util.List;

public interface ITrazabilidadServicePort {

    TrazabilidadModel registrarCambioEstado(TrazabilidadModel model);

    List<TrazabilidadModel> obtenerTrazabilidadPorPedido(Long idPedido);

    List<PedidoEficienciaModel> obtenerEficienciaPedidos();

    List<EmpleadoRankingModel> obtenerRankingEficienciaEmpleados();

    EficienciaResumenModel obtenerResumenEficiencia();
}
