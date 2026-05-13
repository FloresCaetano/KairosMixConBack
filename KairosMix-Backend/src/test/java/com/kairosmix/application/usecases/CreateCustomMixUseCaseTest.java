package com.kairosmix.application.usecases;

import com.kairosmix.domain.entities.CustomMix;
import com.kairosmix.domain.ports.output.CustomMixRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomMixUseCaseTest {
    @Mock private CustomMixRepositoryPort customMixRepository;
    @InjectMocks private CreateCustomMixUseCase useCase;

    @Test
    void testExecuteWithNullMix() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
    }
    
    @Test
    void testExecuteValid() {
        CustomMix mix = new CustomMix();
        mix.setName("Mix");
        mix.setTotalPrice(BigDecimal.ONE);
        
        com.kairosmix.domain.entities.MixComponent component = new com.kairosmix.domain.entities.MixComponent();
        component.setQuantity(1.0);
        component.setUnitPrice(BigDecimal.ONE);
        component.setProduct(new com.kairosmix.domain.entities.Product());
        mix.getComponents().add(component);
        
        when(customMixRepository.save(any())).thenReturn(mix);
        
        CustomMix saved = useCase.execute(mix);
        assertNotNull(saved);
        verify(customMixRepository).save(any());
    }
}
