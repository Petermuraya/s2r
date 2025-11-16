package com.s2r.smarts2r.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupplierProfileRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String businessName;

    private String address;
    private Double latitude;
    private Double longitude;
    private String description;
}
