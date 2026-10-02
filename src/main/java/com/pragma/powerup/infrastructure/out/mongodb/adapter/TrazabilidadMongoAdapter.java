package com.pragma.powerup.infrastructure.out.mongodb.adapter;

import com.pragma.powerup.domain.model.TrazabilidadModel;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.powerup.infrastructure.out.mongodb.entity.TrazabilidadDocument;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ITrazabilidadDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ITrazabilidadMongoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrazabilidadMongoAdapter implements ITrazabilidadPersistencePort {

    private final ITrazabilidadMongoRepository trazabilidadMongoRepository;
    private final ITrazabilidadDocumentMapper trazabilidadDocumentMapper;

    @Override
    public TrazabilidadModel guardarTrazabilidad(TrazabilidadModel model) {
        TrazabilidadDocument document = trazabilidadDocumentMapper.toDocument(model);
        TrazabilidadDocument saved = trazabilidadMongoRepository.save(document);
        return trazabilidadDocumentMapper.toModel(saved);
    }

    @Override
    public List<TrazabilidadModel> obtenerTrazabilidadPorPedido(Long idPedido) {
        List<TrazabilidadDocument> documents = trazabilidadMongoRepository.findByIdPedidoOrderByFechaAsc(idPedido);
        return trazabilidadDocumentMapper.toModelList(documents);
    }
}
