package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.LoginRequest;
import com.s2r.smarts2r.dto.LoginResponse;
import com.s2r.smarts2r.dto.RegisterRequest;
import com.s2r.smarts2r.dto.RefreshRequest;
import com.s2r.smarts2r.service.RefreshTokenService;
import com.s2r.smarts2r.dto.UserResponse;
import com.s2r.smarts2r.service.JwtService;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // REGISTER ENDPOINT
        @PostMapping("/register")
        public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepository.existsByPhone(req.getPhone())) {
            return ResponseEntity.badRequest().body("phone already registered");
        }

        User user = User.builder()
            .fullName(req.getFullName())
            .phone(req.getPhone())
            .email(req.getEmail())
            .passwordHash(passwordEncoder.encode(req.getPassword())) // hashed password
            .role(req.getRole())
            .build();

        User saved = userRepository.save(user);

        // Issue tokens on registration
        String token = jwtService.generateToken(
            saved.getPhone(),
            Map.of("role", saved.getRole().name(), "id", saved.getId())
        );
        String refresh = refreshTokenService.createRefreshToken(saved).getToken();

        LoginResponse resp = LoginResponse.builder()
            .userId(saved.getId())
            .fullName(saved.getFullName())
            .phone(saved.getPhone())
            .role(saved.getRole().name())
            .token(token)
            .refreshToken(refresh)
            .build();

        return ResponseEntity.ok(resp);
        }

    // LOGIN ENDPOINT
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Optional<User> uOpt = userRepository.findByPhone(req.getPhone());
        if (uOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("user not found");
        }

        User user = uOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("invalid password");
        }

        String token = jwtService.generateToken(
            user.getPhone(),
            Map.of("role", user.getRole().name(), "id", user.getId())
        );

        String refresh = refreshTokenService.createRefreshToken(user).getToken();

        LoginResponse resp = LoginResponse.builder()
            .userId(user.getId())
            .fullName(user.getFullName())
            .phone(user.getPhone())
            .role(user.getRole().name())
            .token(token)
            .refreshToken(refresh)
            .build();

        return ResponseEntity.ok(resp);
    }

        @PostMapping("/refresh")
        public ResponseEntity<?> refresh(@Valid @RequestBody RefreshRequest req) {
        String rtoken = req.getRefreshToken();
        if (rtoken == null || rtoken.isBlank()) {
            return ResponseEntity.badRequest().body("refresh token required");
        }

        return refreshTokenService.findByToken(rtoken)
            .map(rt -> {
                if (rt.getExpiresAt().isBefore(java.time.Instant.now())) {
                refreshTokenService.revoke(rtoken);
                return ResponseEntity.badRequest().body("refresh token expired");
                }
                User user = rt.getUser();
                String token = jwtService.generateToken(
                    user.getPhone(), Map.of("role", user.getRole().name(), "id", user.getId())
                );
                return ResponseEntity.ok(Map.of("token", token));
            })
            .orElseGet(() -> ResponseEntity.badRequest().body("invalid refresh token"));
        }
}
