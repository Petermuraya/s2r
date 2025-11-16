package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.RetailerProfileRequest;
import com.s2r.smarts2r.dto.RetailerProfileResponse;
import com.s2r.smarts2r.dto.NearbySuppliersRequest;
import com.s2r.smarts2r.dto.SupplierSuggestionResponse;
import com.s2r.smarts2r.model.RetailerProfile;
import com.s2r.smarts2r.service.RetailerProfileService;
import com.s2r.smarts2r.service.GeolocationService;
import com.s2r.smarts2r.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/retailers")
@RequiredArgsConstructor
public class RetailerProfileController {
    private final RetailerProfileService retailerProfileService;
    private final GeolocationService geolocationService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createProfile(@Valid @RequestBody RetailerProfileRequest req) {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        var uOpt = userRepository.findByPhone(phone);
        if (uOpt.isEmpty()) {
            return ResponseEntity.status(401).body("unauthorized");
        }
        Long userId = uOpt.get().getId();

        RetailerProfile profile = retailerProfileService.createProfile(
                userId,
                req.getShopName(),
                req.getAddress(),
                req.getLatitude(),
                req.getLongitude(),
                req.getDescription()
        );
        return ResponseEntity.ok(mapToResponse(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        return retailerProfileService.getProfile(id)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        return retailerProfileService.getProfileByUserId(userId)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody RetailerProfileRequest req) {
        RetailerProfile profile = retailerProfileService.updateProfile(
                id,
                req.getShopName(),
                req.getAddress(),
                req.getLatitude(),
                req.getLongitude(),
                req.getDescription()
        );
        return ResponseEntity.ok(mapToResponse(profile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        retailerProfileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    // Find nearby suppliers based on location
    @PostMapping("/nearby-suppliers")
    public ResponseEntity<?> getNearbySuppliersFromRequest(@Valid @RequestBody NearbySuppliersRequest req) {
        try {
            List<SupplierSuggestionResponse> suppliers = geolocationService.findNearbySuppliersWithDistance(
                    req.getLatitude(),
                    req.getLongitude(),
                    req.getRadiusKm()
            );
            return ResponseEntity.ok(suppliers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching nearby suppliers: " + e.getMessage());
        }
    }

    // Find nearby suppliers based on retailer's profile location
    @GetMapping("/{retailerId}/nearby-suppliers")
    public ResponseEntity<?> getNearbySuppliersFromProfile(
            @PathVariable Long retailerId,
            @RequestParam(defaultValue = "5.0") Double radiusKm) {
        try {
            return retailerProfileService.getProfile(retailerId)
                    .map(profile -> {
                        if (profile.getLatitude() == null || profile.getLongitude() == null) {
                            return ResponseEntity.badRequest().body("Retailer location not set");
                        }
                        List<SupplierSuggestionResponse> suppliers = geolocationService.findNearbySuppliersWithDistance(
                                profile.getLatitude(),
                                profile.getLongitude(),
                                radiusKm
                        );
                        return ResponseEntity.ok((Object) suppliers);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching nearby suppliers: " + e.getMessage());
        }
    }

    // Find nearby suppliers for authenticated retailer
    @GetMapping("/me/nearby-suppliers")
    public ResponseEntity<?> getNearbySuppliersForMe(@RequestParam(defaultValue = "5.0") Double radiusKm) {
        try {
            String phone = SecurityContextHolder.getContext().getAuthentication().getName();
            var userOpt = userRepository.findByPhone(phone);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body("Unauthorized");
            }

            return retailerProfileService.getProfileByUserId(userOpt.get().getId())
                    .map(profile -> {
                        if (profile.getLatitude() == null || profile.getLongitude() == null) {
                            return ResponseEntity.badRequest().body("Your location not set");
                        }
                        List<SupplierSuggestionResponse> suppliers = geolocationService.findNearbySuppliersWithDistance(
                                profile.getLatitude(),
                                profile.getLongitude(),
                                radiusKm
                        );
                        return ResponseEntity.ok((Object) suppliers);
                    })
                    .orElseGet(() -> ResponseEntity.badRequest().body("Profile not found"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching nearby suppliers: " + e.getMessage());
        }
    }

    private RetailerProfileResponse mapToResponse(RetailerProfile profile) {
        return RetailerProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .shopName(profile.getShopName())
                .address(profile.getAddress())
                .latitude(profile.getLatitude())
                .longitude(profile.getLongitude())
                .description(profile.getDescription())
                .build();
    }
}
