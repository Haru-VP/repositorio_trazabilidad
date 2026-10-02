package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ITrazabilidadServicePort;
import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.domain.spi.ITrazabilidadPersistencePort;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ITrazabilidadDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ITrazabilidadMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private ITrazabilidadMongoRepository mongoRepository;

    @Mock
    private ITrazabilidadDocumentMapper documentMapper;

    @Mock
    private IAuthenticatedUserPort authenticatedUserPort;

    @Test
    void testBeanCreation() {
        BeanConfiguration config = new BeanConfiguration(mongoRepository, documentMapper, authenticatedUserPort);

        ITrazabilidadPersistencePort persistencePort = config.trazabilidadPersistencePort();
        assertNotNull(persistencePort);

        ITrazabilidadServicePort servicePort = config.trazabilidadServicePort();
        assertNotNull(servicePort);
    }
}
