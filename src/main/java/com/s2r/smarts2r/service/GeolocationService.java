package com.s2r.smarts2r.service;

import com.s2r.smarts2r.dto.SupplierSuggestionResponse;
import com.s2r.smarts2r.model.SupplierProfile;
import com.s2r.smarts2r.repository.SupplierProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    private final SupplierProfileRepository supplierProfileRepository;

    // Haversine formula to calculate distance between two coordinates (in km)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public List<SupplierProfile> findNearby(double lat, double lon, double radiusKm) {
        return supplierProfileRepository.findNearby(lat, lon, radiusKm);
    }

    public List<SupplierSuggestionResponse> findNearbySuppliersWithDistance(double lat, double lon, double radiusKm) {
        List<SupplierProfile> suppliers = findNearby(lat, lon, radiusKm);
        return suppliers.stream()
                .map(supplier -> {
                    double distance = calculateDistance(lat, lon, supplier.getLatitude(), supplier.getLongitude());
                    return SupplierSuggestionResponse.builder()
                            .supplierId(supplier.getId())
                            .userId(supplier.getUser().getId())
                            .businessName(supplier.getBusinessName())
                            .address(supplier.getAddress())
                            .phone(supplier.getUser().getPhone())
                            .email(supplier.getUser().getEmail())
                            .latitude(supplier.getLatitude())
                            .longitude(supplier.getLongitude())
                            .description(supplier.getDescription())
                            .distanceKm(Math.round(distance * 100.0) / 100.0) // Round to 2 decimal places
                            .build();
                })
                .sorted((s1, s2) -> Double.compare(s1.getDistanceKm(), s2.getDistanceKm())) // Sort by distance
                .collect(Collectors.toList());
    }
}
