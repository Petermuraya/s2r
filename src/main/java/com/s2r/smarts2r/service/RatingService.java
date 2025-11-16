package com.s2r.smarts2r.service;

import com.s2r.smarts2r.model.Rating;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.RatingRepository;
import com.s2r.smarts2r.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public Rating submitRating(Long retailerId, Long supplierId, Integer score, String comment) {
        User supplier = userRepository.findById(supplierId).orElseThrow(() -> new IllegalArgumentException("supplier not found"));
        User retailer = userRepository.findById(retailerId).orElseThrow(() -> new IllegalArgumentException("retailer not found"));
        Rating r = Rating.builder()
                .supplier(supplier)
                .retailer(retailer)
                .score(score)
                .comment(comment)
                .build();
        return ratingRepository.save(r);
    }

    public Map<String, Object> getStats(Long supplierId) {
        Double avg = ratingRepository.findAverageScoreBySupplierId(supplierId);
        Long cnt = ratingRepository.countBySupplierId(supplierId);
        return Map.of("average", avg == null ? 0.0 : avg, "count", cnt == null ? 0L : cnt);
    }
}
