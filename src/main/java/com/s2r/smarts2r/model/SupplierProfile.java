package com.s2r.smarts2r.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supplier_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String businessName;

    private String address;

    private Double latitude;
    private Double longitude;

    private String description;
}
