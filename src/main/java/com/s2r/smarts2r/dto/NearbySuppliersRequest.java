package com.s2r.smarts2r.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NearbySuppliersRequest {
    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @Positive(message = "Radius must be positive")
    @Builder.Default
    private Double radiusKm = 5.0; // Default 5km radius
}
