package com.s2r.smarts2r.service;

import com.s2r.smarts2r.model.SupplierProfile;
import com.s2r.smarts2r.repository.SupplierProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    private final SupplierProfileRepository supplierProfileRepository;

    public List<SupplierProfile> findNearby(double lat, double lon, double radiusKm) {
        return supplierProfileRepository.findNearby(lat, lon, radiusKm);
    }
}
