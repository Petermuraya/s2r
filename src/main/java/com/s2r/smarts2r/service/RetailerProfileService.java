package com.s2r.smarts2r.service;

import com.s2r.smarts2r.model.RetailerProfile;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.RetailerProfileRepository;
import com.s2r.smarts2r.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetailerProfileService {
    private final RetailerProfileRepository retailerProfileRepository;
    private final UserRepository userRepository;

    public RetailerProfile createProfile(Long userId, String shopName, String address, Double latitude, Double longitude, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        RetailerProfile profile = RetailerProfile.builder()
                .user(user)
                .shopName(shopName)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                .build();

        return retailerProfileRepository.save(profile);
    }

    public Optional<RetailerProfile> getProfile(Long id) {
        return retailerProfileRepository.findById(id);
    }

    public Optional<RetailerProfile> getProfileByUserId(Long userId) {
        return retailerProfileRepository.findByUserId(userId);
    }

    public RetailerProfile updateProfile(Long id, String shopName, String address, Double latitude, Double longitude, String description) {
        RetailerProfile profile = retailerProfileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        profile.setShopName(shopName);
        profile.setAddress(address);
        profile.setLatitude(latitude);
        profile.setLongitude(longitude);
        profile.setDescription(description);

        return retailerProfileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        retailerProfileRepository.deleteById(id);
    }
}
