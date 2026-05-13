package com.kairosmix.infrastructure.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kairosmix.domain.entities.Order;
import com.kairosmix.domain.ports.output.OrderRepositoryPort;
import com.kairosmix.infrastructure.rest.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepositoryPort orderRepository;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = Order.builder()
            .status(Order.OrderStatus.PENDING)
            .totalPrice(java.math.BigDecimal.valueOf(250.0))
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Test
    void testListOrders() throws Exception {
        mockMvc.perform(get("/v1/orders")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetOrderById() throws Exception {
        Order saved = orderRepository.save(testOrder);
        
        mockMvc.perform(get("/v1/orders/" + saved.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        Order saved = orderRepository.save(testOrder);
        
        mockMvc.perform(put("/v1/orders/" + saved.getId() + "/status")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"status\":\"PROCESSING\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void testDeleteOrder() throws Exception {
        Order saved = orderRepository.save(testOrder);
        
        mockMvc.perform(delete("/v1/orders/" + saved.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
