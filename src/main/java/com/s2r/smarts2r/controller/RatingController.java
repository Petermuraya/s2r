package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.RatingRequest;
import com.s2r.smarts2r.dto.RatingStatsResponse;
import com.s2r.smarts2r.model.Rating;
import com.s2r.smarts2r.service.RatingService;
import com.s2r.smarts2r.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> submitRating(@Valid @RequestBody RatingRequest req) {
        String phone = SecurityContextHolder.getContext().getAuthentication().getName();
        var uOpt = userRepository.findByPhone(phone);
        if (uOpt.isEmpty()) {
            return ResponseEntity.status(401).body("unauthorized");
        }
        Long retailerId = uOpt.get().getId();
        Rating r = ratingService.submitRating(retailerId, req.getSupplierId(), req.getScore(), req.getComment());
        return ResponseEntity.ok(r);
    }

    @GetMapping("/supplier/{id}/stats")
    public ResponseEntity<?> getStats(@PathVariable Long id) {
        Map<String, Object> stats = ratingService.getStats(id);
        RatingStatsResponse resp = RatingStatsResponse.builder()
                .average((Double) stats.get("average"))
                .count((Long) stats.get("count"))
                .build();
        return ResponseEntity.ok(resp);
    }
}
