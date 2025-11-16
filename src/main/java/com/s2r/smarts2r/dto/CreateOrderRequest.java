package com.s2r.smarts2r.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateOrderRequest {
    private Long supplierId;
    private BigDecimal totalAmount;
    private String notes;
}
