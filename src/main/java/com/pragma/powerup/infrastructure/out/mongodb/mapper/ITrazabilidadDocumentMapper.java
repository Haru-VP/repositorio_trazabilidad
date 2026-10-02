package com.pragma.powerup.infrastructure.out.mongodb.mapper;

import com.pragma.powerup.domain.model.TrazabilidadModel;
import com.pragma.powerup.infrastructure.out.mongodb.entity.TrazabilidadDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITrazabilidadDocumentMapper {

    TrazabilidadDocument toDocument(TrazabilidadModel model);

    TrazabilidadModel toModel(TrazabilidadDocument document);

    List<TrazabilidadModel> toModelList(List<TrazabilidadDocument> documentList);
}
