package com.s2r.smarts2r.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequest {
    @NotNull
    private Long supplierId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    private String comment;
}
