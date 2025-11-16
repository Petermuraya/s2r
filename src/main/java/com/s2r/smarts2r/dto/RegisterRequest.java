package com.s2r.smarts2r.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.s2r.smarts2r.model.Role;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Role role;
}
