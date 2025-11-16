package com.s2r.smarts2r.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupplierProfileResponse {
    private Long id;
    private Long userId;
    private String businessName;
    private String address;
    private Double latitude;
    private Double longitude;
    private String description;
}
