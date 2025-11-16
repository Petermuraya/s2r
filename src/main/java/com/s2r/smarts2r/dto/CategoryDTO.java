package com.s2r.smarts2r.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private String iconUrl;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
