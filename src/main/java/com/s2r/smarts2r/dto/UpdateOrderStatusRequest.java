package com.s2r.smarts2r.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateOrderStatusRequest {
    private String status;
}
