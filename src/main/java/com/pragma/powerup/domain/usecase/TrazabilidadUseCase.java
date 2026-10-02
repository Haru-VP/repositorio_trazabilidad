package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.exception.ParametrosInvalidosException;
import com.pragma.powerup.domain.exception.PedidoNoEncontradoException;
import com.pragma.powerup.domain.exception.UsuarioNoAutorizadoException;
import com.pragma.powerup.domain.model.TrazabilidadModel;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrazabilidadUseCase implements ITrazabilidadServicePort {

    private final ITrazabilidadPersistencePort trazabilidadPersistencePort;
    private final IAuthenticatedUserPort authenticatedUserPort;

    public TrazabilidadUseCase(ITrazabilidadPersistencePort trazabilidadPersistencePort,
                               IAuthenticatedUserPort authenticatedUserPort) {
        this.trazabilidadPersistencePort = trazabilidadPersistencePort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Override
    public TrazabilidadModel registrarCambioEstado(TrazabilidadModel model) {
        if (model == null || model.getIdPedido() == null) {
            throw new ParametrosInvalidosException();
        }

        if (model.getFecha() == null) {
            model.setFecha(LocalDateTime.now());
        }

        return trazabilidadPersistencePort.guardarTrazabilidad(model);
    }

    @Override
    public List<TrazabilidadModel> obtenerTrazabilidadPorPedido(Long idPedido) {
        if (idPedido == null || idPedido <= 0) {
            throw new ParametrosInvalidosException();
        }

        Long idUsuarioAutenticado = authenticatedUserPort.obtenerIdUsuarioAutenticado();
        if (idUsuarioAutenticado == null) {
            throw new UsuarioNoAutorizadoException();
        }

        List<TrazabilidadModel> registros = trazabilidadPersistencePort.obtenerTrazabilidadPorPedido(idPedido);
        if (registros == null || registros.isEmpty()) {
            throw new PedidoNoEncontradoException();
        }

        String rol = authenticatedUserPort.obtenerRolUsuarioAutenticado();
        // Si el usuario es CLIENTE, solo puede consultar logs de sus propios pedidos
        if ("CLIENTE".equalsIgnoreCase(rol) || "ROLE_CLIENTE".equalsIgnoreCase(rol)) {
            boolean perteneceAlCliente = registros.stream()
                    .allMatch(r -> idUsuarioAutenticado.equals(r.getIdCliente()));
            if (!perteneceAlCliente) {
                throw new UsuarioNoAutorizadoException();
            }
        }

        return registros.stream()
                .sorted(Comparator.comparing(TrazabilidadModel::getFecha))
                .collect(Collectors.toList());
    }
}
