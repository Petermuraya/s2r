package com.s2r.smarts2r.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RetailerProfileResponse {
    private Long id;
    private Long userId;
    private String shopName;
    private String address;
    private Double latitude;
    private Double longitude;
    private String description;
}
