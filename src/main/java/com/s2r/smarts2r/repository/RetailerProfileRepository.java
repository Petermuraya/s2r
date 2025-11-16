package com.s2r.smarts2r.repository;

import com.s2r.smarts2r.model.RetailerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetailerProfileRepository extends JpaRepository<RetailerProfile, Long> {
    Optional<RetailerProfile> findByUserId(Long userId);
}
