package com.s2r.smarts2r.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupplierSuggestionResponse {
    private Long supplierId;
    private Long userId;
    private String businessName;
    private String address;
    private String phone;
    private String email;
    private Double latitude;
    private Double longitude;
    private String description;
    private Double distanceKm; // Distance from retailer's location
}
