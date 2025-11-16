package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.RetailerProfileRequest;
import com.s2r.smarts2r.dto.RetailerProfileResponse;
import com.s2r.smarts2r.model.RetailerProfile;
import com.s2r.smarts2r.service.RetailerProfileService;
import com.s2r.smarts2r.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/retailers")
@RequiredArgsConstructor
public class RetailerProfileController {
    private final RetailerProfileService retailerProfileService;
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
