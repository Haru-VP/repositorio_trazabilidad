package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.exception.ParametrosInvalidosException;
import com.pragma.powerup.domain.exception.PedidoNoEncontradoException;
import com.pragma.powerup.domain.exception.UsuarioNoAutorizadoException;
import com.pragma.powerup.domain.model.EficienciaResumenModel;
import com.pragma.powerup.domain.model.EmpleadoRankingModel;
import com.pragma.powerup.domain.model.PedidoEficienciaModel;
import com.pragma.powerup.domain.model.TrazabilidadModel;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public List<PedidoEficienciaModel> obtenerEficienciaPedidos() {
        validarRolPropietario();
        List<TrazabilidadModel> todosLosRegistros = trazabilidadPersistencePort.obtenerTodosLosRegistros();
        if (todosLosRegistros == null || todosLosRegistros.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<TrazabilidadModel>> registrosPorPedido = todosLosRegistros.stream()
                .filter(r -> r.getIdPedido() != null && r.getFecha() != null)
                .collect(Collectors.groupingBy(TrazabilidadModel::getIdPedido));

        List<PedidoEficienciaModel> eficienciaPedidos = new ArrayList<>();

        for (Map.Entry<Long, List<TrazabilidadModel>> entry : registrosPorPedido.entrySet()) {
            Long idPedido = entry.getKey();
            List<TrazabilidadModel> logs = entry.getValue().stream()
                    .sorted(Comparator.comparing(TrazabilidadModel::getFecha))
                    .collect(Collectors.toList());

            if (logs.isEmpty()) {
                continue;
            }

            TrazabilidadModel logInicial = logs.get(0);
            LocalDateTime fechaInicio = logInicial.getFecha();
            Long idCliente = logInicial.getIdCliente();
            String correoCliente = logInicial.getCorreoCliente();

            Optional<TrazabilidadModel> logEntregaOpt = logs.stream()
                    .filter(l -> "ENTREGADO".equalsIgnoreCase(l.getNuevoEstado()))
                    .findFirst();

            if (logEntregaOpt.isPresent()) {
                TrazabilidadModel logEntrega = logEntregaOpt.get();
                LocalDateTime fechaFin = logEntrega.getFecha();

                Long idEmpleado = logs.stream()
                        .filter(l -> l.getIdEmpleado() != null)
                        .map(TrazabilidadModel::getIdEmpleado)
                        .reduce((first, second) -> second)
                        .orElse(logEntrega.getIdEmpleado());

                String correoEmpleado = logs.stream()
                        .filter(l -> l.getCorreoEmpleado() != null)
                        .map(TrazabilidadModel::getCorreoEmpleado)
                        .reduce((first, second) -> second)
                        .orElse(logEntrega.getCorreoEmpleado());

                long duracionSegundos = Math.max(0, Duration.between(fechaInicio, fechaFin).getSeconds());
                double duracionMinutos = Math.round((duracionSegundos / 60.0) * 100.0) / 100.0;
                String duracionFormateada = formatearDuracion(duracionSegundos);

                eficienciaPedidos.add(new PedidoEficienciaModel(
                        idPedido, idCliente, correoCliente, idEmpleado, correoEmpleado,
                        fechaInicio, fechaFin, duracionSegundos, duracionMinutos, duracionFormateada, "ENTREGADO"
                ));
            }
        }

        return eficienciaPedidos.stream()
                .sorted(Comparator.comparing(PedidoEficienciaModel::getIdPedido))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoRankingModel> obtenerRankingEficienciaEmpleados() {
        List<PedidoEficienciaModel> pedidosEntregados = obtenerEficienciaPedidos();
        if (pedidosEntregados.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<PedidoEficienciaModel>> pedidosPorEmpleado = pedidosEntregados.stream()
                .filter(p -> p.getIdEmpleado() != null)
                .collect(Collectors.groupingBy(PedidoEficienciaModel::getIdEmpleado));

        List<EmpleadoRankingModel> rankingList = new ArrayList<>();

        for (Map.Entry<Long, List<PedidoEficienciaModel>> entry : pedidosPorEmpleado.entrySet()) {
            Long idEmpleado = entry.getKey();
            List<PedidoEficienciaModel> pedidos = entry.getValue();
            int cantidadPedidos = pedidos.size();

            double tiempoMedioSegundos = pedidos.stream()
                    .mapToLong(PedidoEficienciaModel::getDuracionSegundos)
                    .average()
                    .orElse(0.0);
            tiempoMedioSegundos = Math.round(tiempoMedioSegundos * 100.0) / 100.0;

            double tiempoMedioMinutos = Math.round((tiempoMedioSegundos / 60.0) * 100.0) / 100.0;
            String tiempoMedioFormateado = formatearDuracion((long) tiempoMedioSegundos);

            String correoEmpleado = pedidos.stream()
                    .map(PedidoEficienciaModel::getCorreoEmpleado)
                    .filter(c -> c != null && !c.trim().isEmpty())
                    .findFirst()
                    .orElse(null);

            rankingList.add(new EmpleadoRankingModel(
                    0, idEmpleado, correoEmpleado, cantidadPedidos,
                    tiempoMedioSegundos, tiempoMedioMinutos, tiempoMedioFormateado
            ));
        }

        rankingList.sort(Comparator.comparing(EmpleadoRankingModel::getTiempoMedioSegundos));

        for (int i = 0; i < rankingList.size(); i++) {
            rankingList.get(i).setRanking(i + 1);
        }

        return rankingList;
    }

    @Override
    public EficienciaResumenModel obtenerResumenEficiencia() {
        return new EficienciaResumenModel(obtenerEficienciaPedidos(), obtenerRankingEficienciaEmpleados());
    }

    private void validarRolPropietario() {
        Long idUsuario = authenticatedUserPort.obtenerIdUsuarioAutenticado();
        if (idUsuario == null) {
            throw new UsuarioNoAutorizadoException();
        }
        String rol = authenticatedUserPort.obtenerRolUsuarioAutenticado();
        if (rol == null || (!rol.toUpperCase().contains("PROPIETARIO") && !rol.toUpperCase().contains("ADMINISTRADOR"))) {
            throw new UsuarioNoAutorizadoException();
        }
    }

    private String formatearDuracion(long totalSegundos) {
        if (totalSegundos < 0) {
            totalSegundos = 0;
        }
        long horas = totalSegundos / 3600;
        long minutos = (totalSegundos % 3600) / 60;
        long segundos = totalSegundos % 60;

        if (horas > 0) {
            return String.format("%dh %dm %ds", horas, minutos, segundos);
        } else if (minutos > 0) {
            return String.format("%dm %ds", minutos, segundos);
        } else {
            return String.format("%ds", segundos);
        }
    }
}
