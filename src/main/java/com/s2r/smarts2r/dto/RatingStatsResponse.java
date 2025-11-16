package com.s2r.smarts2r.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingStatsResponse {
    private Double average;
    private Long count;
}
