package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.CreateOrderRequest;
import com.s2r.smarts2r.dto.OrderResponse;
import com.s2r.smarts2r.dto.UpdateOrderStatusRequest;
import com.s2r.smarts2r.model.Order;
import com.s2r.smarts2r.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final com.s2r.smarts2r.repository.UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest req) {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        var uOpt = userRepository.findByPhone(phone);
        if (uOpt.isEmpty()) {
            return ResponseEntity.status(401).body("unauthorized");
        }
        Long retailerId = uOpt.get().getId();
        if (req.getSupplierId() == null) {
            return ResponseEntity.badRequest().body("supplierId required");
        }

        Order order = orderService.createOrder(retailerId, req.getSupplierId(), req.getTotalAmount(), req.getNotes());
        return ResponseEntity.ok(mapToResponse(order));
    }

    @GetMapping("/retailer/{id}")
    public ResponseEntity<?> getRetailerOrders(@PathVariable Long id) {
        List<Order> orders = orderService.getRetailerOrders(id);
        List<OrderResponse> responses = orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/supplier/{id}")
    public ResponseEntity<?> getSupplierOrders(@PathVariable Long id) {
        List<Order> orders = orderService.getSupplierOrders(id);
        List<OrderResponse> responses = orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest req) {
        Order order = orderService.updateOrderStatus(id, req.getStatus());
        return ResponseEntity.ok(mapToResponse(order));
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .retailerId(order.getRetailer().getId())
                .supplierId(order.getSupplier().getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .deliveredAt(order.getDeliveredAt())
                .build();
    }
}
