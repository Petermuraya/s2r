package com.s2r.smarts2r.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RetailerProfileRequest {
    @NotBlank
    private String shopName;

    private String address;
    private Double latitude;
    private Double longitude;
    private String description;
}
