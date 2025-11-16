package com.s2r.smarts2r.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s2r.smarts2r.dto.LoginRequest;
import com.s2r.smarts2r.dto.RegisterRequest;
import com.s2r.smarts2r.model.Role;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.UserRepository;
import com.s2r.smarts2r.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @Test
    void register_success_returnsUserResponse() throws Exception {
        RegisterRequest req = RegisterRequest.builder()
                .fullName("Alice")
                .phone("1234567890")
                .email("alice@example.com")
                .password("pw")
                .role(Role.SUPPLIER)
                .build();

        Mockito.when(userRepository.existsByPhone(req.getPhone())).thenReturn(false);

        User saved = User.builder()
                .id(1L)
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .email(req.getEmail())
                .role(req.getRole())
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(saved);

        mvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Alice"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.role").value("SUPPLIER"));
    }

    @Test
    void login_success_returnsToken() throws Exception {
        String rawPassword = "SecretPass123";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(rawPassword);

        User user = User.builder()
                .id(2L)
                .fullName("Bob")
                .phone("0987654321")
                .passwordHash(hash)
                .role(Role.RETAILER)
                .build();

        Mockito.when(userRepository.findByPhone(user.getPhone())).thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(Mockito.eq(user.getPhone()), Mockito.anyMap())).thenReturn("token123");

        LoginRequest req = LoginRequest.builder().phone(user.getPhone()).password(rawPassword).build();

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }
}
