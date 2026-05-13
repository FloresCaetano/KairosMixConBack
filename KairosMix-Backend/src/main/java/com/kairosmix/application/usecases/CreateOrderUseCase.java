package com.kairosmix.application.usecases;

import com.kairosmix.domain.entities.Order;
import com.kairosmix.domain.entities.OrderItem;
import com.kairosmix.domain.entities.Product;
import com.kairosmix.domain.ports.output.OrderRepositoryPort;
import com.kairosmix.domain.ports.output.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderRepositoryPort orderRepository;
    private final ProductRepositoryPort productRepository;

    public Order execute(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("La orden no puede ser nula");
        }

        // Validar que tenga items
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("La orden debe tener al menos un item");
        }

        // Validar y procesar items
        for (OrderItem item : order.getItems()) {
            item.validateItem();

            Product product = productRepository.findById(item.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + item.getProduct().getId()));

            // Verificar stock disponible
            if (product.getCurrentStock() < item.getQuantity()) {
                throw new IllegalArgumentException(
                    String.format("Stock insuficiente para %s. Disponible: %d, Solicitado: %d",
                        product.getName(), product.getCurrentStock(), item.getQuantity())
                );
            }

            // Reducir stock
            product.reduceStock(item.getQuantity());
            productRepository.save(product);
        }

        // Calcular total
        order.calculateTotal();

        // Validar orden
        order.validateOrder();

        return orderRepository.save(order);
    }
}
