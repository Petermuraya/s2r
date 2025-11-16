package com.s2r.smarts2r.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "retailer_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RetailerProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String shopName;

    private String address;

    private Double latitude;
    private Double longitude;

    private String description;
}
