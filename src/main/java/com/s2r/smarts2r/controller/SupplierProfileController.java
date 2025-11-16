package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.SupplierProfileRequest;
import com.s2r.smarts2r.model.SupplierProfile;
import com.s2r.smarts2r.dto.SupplierProfileResponse;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.SupplierProfileRepository;
import com.s2r.smarts2r.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import com.s2r.smarts2r.service.GeolocationService;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierProfileController {
    private final SupplierProfileRepository supplierProfileRepository;
    private final UserRepository userRepository;
    private final GeolocationService geolocationService;

    @PostMapping
    public ResponseEntity<?> createSupplier(@Valid @RequestBody SupplierProfileRequest req) {
        Optional<User> uOpt = userRepository.findById(req.getUserId());
        if (uOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("user not found");
        }
        User user = uOpt.get();
        if (user.getRole() != com.s2r.smarts2r.model.Role.SUPPLIER) {
            return ResponseEntity.badRequest().body("user is not a supplier role");
        }

        SupplierProfile sp = SupplierProfile.builder()
                .user(user)
                .businessName(req.getBusinessName())
                .address(req.getAddress())
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .description(req.getDescription())
                .build();

        SupplierProfile saved = supplierProfileRepository.save(sp);

        SupplierProfileResponse resp = SupplierProfileResponse.builder()
            .id(saved.getId())
            .userId(saved.getUser().getId())
            .businessName(saved.getBusinessName())
            .address(saved.getAddress())
            .latitude(saved.getLatitude())
            .longitude(saved.getLongitude())
            .description(saved.getDescription())
            .build();

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbySuppliers(@RequestParam double lat, @RequestParam double lon, @RequestParam(defaultValue = "10") double radiusKm) {
        List<com.s2r.smarts2r.model.SupplierProfile> nearby = geolocationService.findNearby(lat, lon, radiusKm);
        List<SupplierProfileResponse> resp = nearby.stream().map(sp -> SupplierProfileResponse.builder()
                .id(sp.getId())
                .userId(sp.getUser().getId())
                .businessName(sp.getBusinessName())
                .address(sp.getAddress())
                .latitude(sp.getLatitude())
                .longitude(sp.getLongitude())
                .description(sp.getDescription())
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }
}
