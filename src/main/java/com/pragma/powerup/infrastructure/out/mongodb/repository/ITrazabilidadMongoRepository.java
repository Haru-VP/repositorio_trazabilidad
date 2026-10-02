package com.pragma.powerup.infrastructure.out.mongodb.repository;

import com.pragma.powerup.infrastructure.out.mongodb.entity.TrazabilidadDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITrazabilidadMongoRepository extends MongoRepository<TrazabilidadDocument, String> {

    List<TrazabilidadDocument> findByIdPedidoOrderByFechaAsc(Long idPedido);
}
