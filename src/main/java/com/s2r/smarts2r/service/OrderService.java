package com.s2r.smarts2r.service;

import com.s2r.smarts2r.model.Order;
import com.s2r.smarts2r.model.OrderStatus;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.OrderRepository;
import com.s2r.smarts2r.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Order createOrder(Long retailerId, Long supplierId, java.math.BigDecimal amount, String notes) {
        User retailer = userRepository.findById(retailerId)
                .orElseThrow(() -> new IllegalArgumentException("Retailer not found"));
        User supplier = userRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Order order = Order.builder()
                .retailer(retailer)
                .supplier(supplier)
                .status(OrderStatus.PENDING)
                .totalAmount(amount)
                .notes(notes)
                .build();

        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getRetailerOrders(Long retailerId) {
        return orderRepository.findByRetailerId(retailerId);
    }

    public List<Order> getSupplierOrders(Long supplierId) {
        return orderRepository.findBySupplierId(supplierId);
    }

    public Order updateOrderStatus(Long id, String statusStr) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        try {
            OrderStatus newStatus = OrderStatus.valueOf(statusStr.toUpperCase());
            order.setStatus(newStatus);
            order.setUpdatedAt(OffsetDateTime.now());

            if (newStatus == OrderStatus.DELIVERED) {
                order.setDeliveredAt(OffsetDateTime.now());
            }

            return orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + statusStr);
        }
    }
}
