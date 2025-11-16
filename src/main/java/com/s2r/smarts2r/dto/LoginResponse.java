package com.s2r.smarts2r.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {
    private Long userId;
    private String fullName;
    private String phone;
    private String role;
    private String token; // access token
    private String refreshToken; // refresh token
}
