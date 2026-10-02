package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.powerup.domain.usecase.TrazabilidadUseCase;
import com.pragma.powerup.infrastructure.out.mongodb.adapter.TrazabilidadMongoAdapter;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ITrazabilidadDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ITrazabilidadMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITrazabilidadMongoRepository trazabilidadMongoRepository;
    private final ITrazabilidadDocumentMapper trazabilidadDocumentMapper;
    private final IAuthenticatedUserPort authenticatedUserPort;

    @Bean
    public ITrazabilidadPersistencePort trazabilidadPersistencePort() {
        return new TrazabilidadMongoAdapter(trazabilidadMongoRepository, trazabilidadDocumentMapper);
    }

    @Bean
    public ITrazabilidadServicePort trazabilidadServicePort() {
        return new TrazabilidadUseCase(trazabilidadPersistencePort(), authenticatedUserPort);
    }
}
