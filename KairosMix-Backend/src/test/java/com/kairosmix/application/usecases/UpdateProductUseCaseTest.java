package com.kairosmix.application.usecases;

import com.kairosmix.domain.entities.Product;
import com.kairosmix.domain.ports.output.ProductRepositoryPort;
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
class UpdateProductUseCaseTest {
    @Mock private ProductRepositoryPort productRepository;
    @InjectMocks private UpdateProductUseCase useCase;

    @Test
    void testExecute() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(1L, null));
    }
    
    @Test
    void testExecuteValid() {
        Product p = new Product();
        p.setId(1L);
        p.setCode("123");
        p.setName("N");
        p.setCountryOfOrigin("CO");
        p.setPricePerPound(BigDecimal.ONE);
        p.setWholesalePrice(BigDecimal.ONE);
        p.setRetailPrice(BigDecimal.ONE);
        p.setInitialStock(10);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productRepository.save(any())).thenReturn(p);
        
        Product updated = useCase.execute(1L, p);
        assertNotNull(updated);
        verify(productRepository).save(any());
    }
}
