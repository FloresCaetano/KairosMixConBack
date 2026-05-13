package com.kairosmix.application.usecases;

import com.kairosmix.domain.entities.Order;
import com.kairosmix.domain.ports.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {
    @Mock private OrderRepositoryPort orderRepository;
    @InjectMocks private UpdateOrderStatusUseCase useCase;

    @Test
    void testExecute() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(1L, null));
    }
    
    @Test
    void testExecuteValid() {
        Order order = new Order();
        order.setStatus(Order.OrderStatus.PENDING);
        
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);
        
        Order updated = useCase.execute(1L, Order.OrderStatus.PROCESSING);
        assertNotNull(updated);
        assertEquals(Order.OrderStatus.PROCESSING, updated.getStatus());
        verify(orderRepository).save(any());
    }
}
