package com.kairosmix.infrastructure.persistence;

import com.kairosmix.domain.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderRepositoryAdapterTest {

    @Autowired
    private OrderRepositoryAdapter orderRepositoryAdapter;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = Order.builder()
            .status(Order.OrderStatus.PENDING)
            .totalPrice(java.math.BigDecimal.valueOf(100.0))
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    void testSaveOrder() {
        Order saved = orderRepositoryAdapter.save(testOrder);
        
        assertNotNull(saved.getId());
        assertEquals(testOrder.getStatus(), saved.getStatus());
    }

    @Test
    void testFindOrderById() {
        Order saved = orderRepositoryAdapter.save(testOrder);
        Optional<Order> found = orderRepositoryAdapter.findById(saved.getId());
        
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testFindAllOrders() {
        orderRepositoryAdapter.save(testOrder);
        List<Order> all = orderRepositoryAdapter.findAll();
        
        assertFalse(all.isEmpty());
    }

    @Test
    void testDeleteOrder() {
        Order saved = orderRepositoryAdapter.save(testOrder);
        orderRepositoryAdapter.delete(saved);
        Optional<Order> found = orderRepositoryAdapter.findById(saved.getId());
        
        assertTrue(found.isEmpty());
    }

    @Test
    void testUpdateOrderStatus() {
        Order saved = orderRepositoryAdapter.save(testOrder);
        saved.setStatus(Order.OrderStatus.PROCESSING);
        Order updated = orderRepositoryAdapter.save(saved);
        
        assertEquals(Order.OrderStatus.PROCESSING, updated.getStatus());
    }
}
