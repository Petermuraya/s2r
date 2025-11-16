package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.CreateOrderRequest;
import com.s2r.smarts2r.dto.OrderResponse;
import com.s2r.smarts2r.dto.UpdateOrderStatusRequest;
import com.s2r.smarts2r.model.Order;
import com.s2r.smarts2r.model.OrderStatus;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.OrderRepository;
import com.s2r.smarts2r.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_success() throws Exception {
        User retailer = User.builder().id(1L).fullName("Retailer").phone("111").build();
        User supplier = User.builder().id(2L).fullName("Supplier").phone("222").build();

        Order order = Order.builder()
                .id(1L)
                .retailer(retailer)
                .supplier(supplier)
                .status(OrderStatus.PENDING)
                .totalAmount(new BigDecimal("100.00"))
                .notes("test")
                .build();

        Mockito.when(orderService.createOrder(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.anyString()))
                .thenReturn(order);

        CreateOrderRequest req = CreateOrderRequest.builder()
                .supplierId(2L)
                .totalAmount(new BigDecimal("100.00"))
                .notes("test")
                .build();

        mvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"supplierId":2,"totalAmount":100.00,"notes":"test"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getOrder_success() throws Exception {
        User retailer = User.builder().id(1L).fullName("Retailer").phone("111").build();
        User supplier = User.builder().id(2L).fullName("Supplier").phone("222").build();

        Order order = Order.builder()
                .id(1L)
                .retailer(retailer)
                .supplier(supplier)
                .status(OrderStatus.PENDING)
                .totalAmount(new BigDecimal("100.00"))
                .createdAt(OffsetDateTime.now())
                .build();

        Mockito.when(orderService.getOrder(1L)).thenReturn(Optional.of(order));

        mvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void updateStatus_success() throws Exception {
        User retailer = User.builder().id(1L).fullName("Retailer").phone("111").build();
        User supplier = User.builder().id(2L).fullName("Supplier").phone("222").build();

        Order order = Order.builder()
                .id(1L)
                .retailer(retailer)
                .supplier(supplier)
                .status(OrderStatus.ACCEPTED)
                .totalAmount(new BigDecimal("100.00"))
                .updatedAt(OffsetDateTime.now())
                .build();

        Mockito.when(orderService.updateOrderStatus(1L, "ACCEPTED")).thenReturn(order);

        mvc.perform(patch("/api/v1/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"status":"ACCEPTED"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }
}
