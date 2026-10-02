package com.pragma.powerup.infrastructure.out.mongodb.adapter;

import com.pragma.powerup.domain.model.TrazabilidadModel;
import com.pragma.powerup.infrastructure.out.mongodb.entity.TrazabilidadDocument;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ITrazabilidadDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ITrazabilidadMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrazabilidadMongoAdapterTest {

    @Mock
    private ITrazabilidadMongoRepository trazabilidadMongoRepository;

    @Mock
    private ITrazabilidadDocumentMapper trazabilidadDocumentMapper;

    @InjectMocks
    private TrazabilidadMongoAdapter trazabilidadMongoAdapter;

    @Test
    void guardarTrazabilidad_exitoso() {
        // Arrange
        TrazabilidadModel model = new TrazabilidadModel();
        TrazabilidadDocument document = new TrazabilidadDocument();

        when(trazabilidadDocumentMapper.toDocument(model)).thenReturn(document);
        when(trazabilidadMongoRepository.save(document)).thenReturn(document);
        when(trazabilidadDocumentMapper.toModel(document)).thenReturn(model);

        // Act
        TrazabilidadModel resultado = trazabilidadMongoAdapter.guardarTrazabilidad(model);

        // Assert
        assertNotNull(resultado);
        verify(trazabilidadDocumentMapper, times(1)).toDocument(model);
        verify(trazabilidadMongoRepository, times(1)).save(document);
        verify(trazabilidadDocumentMapper, times(1)).toModel(document);
    }

    @Test
    void obtenerTrazabilidadPorPedido_exitoso() {
        // Arrange
        Long idPedido = 1L;
        TrazabilidadDocument document = new TrazabilidadDocument();
        TrazabilidadModel model = new TrazabilidadModel();

        when(trazabilidadMongoRepository.findByIdPedidoOrderByFechaAsc(idPedido))
                .thenReturn(Collections.singletonList(document));
        when(trazabilidadDocumentMapper.toModelList(Collections.singletonList(document)))
                .thenReturn(Collections.singletonList(model));

        // Act
        List<TrazabilidadModel> resultado = trazabilidadMongoAdapter.obtenerTrazabilidadPorPedido(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(trazabilidadMongoRepository, times(1)).findByIdPedidoOrderByFechaAsc(idPedido);
        verify(trazabilidadDocumentMapper, times(1)).toModelList(Collections.singletonList(document));
    }
}
