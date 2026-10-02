package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.TrazabilidadModel;

import java.util.List;

public interface ITrazabilidadPersistencePort {

    TrazabilidadModel guardarTrazabilidad(TrazabilidadModel model);

    List<TrazabilidadModel> obtenerTrazabilidadPorPedido(Long idPedido);
}
