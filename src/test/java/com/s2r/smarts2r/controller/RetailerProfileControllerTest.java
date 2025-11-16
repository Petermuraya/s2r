package com.s2r.smarts2r.controller;

import com.s2r.smarts2r.dto.RetailerProfileRequest;
import com.s2r.smarts2r.dto.RetailerProfileResponse;
import com.s2r.smarts2r.model.RetailerProfile;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.service.RetailerProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RetailerProfileController.class)
public class RetailerProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RetailerProfileService retailerProfileService;

    @Test
    void createProfile_success() throws Exception {
        User user = User.builder().id(1L).fullName("John").phone("555").build();
        RetailerProfile profile = RetailerProfile.builder()
                .id(1L)
                .user(user)
                .shopName("John's Shop")
                .address("123 Main St")
                .latitude(-1.2865)
                .longitude(36.8172)
                .description("Quality retail")
                .build();

        Mockito.when(retailerProfileService.createProfile(
                Mockito.anyLong(),
                Mockito.eq("John's Shop"),
                Mockito.anyString(),
                Mockito.anyDouble(),
                Mockito.anyDouble(),
                Mockito.anyString()
        )).thenReturn(profile);

        mvc.perform(post("/api/v1/retailers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"shopName":"John's Shop","address":"123 Main St","latitude":-1.2865,"longitude":36.8172,"description":"Quality retail"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.shopName").value("John's Shop"));
    }

    @Test
    void getProfile_success() throws Exception {
        User user = User.builder().id(1L).fullName("John").phone("555").build();
        RetailerProfile profile = RetailerProfile.builder()
                .id(1L)
                .user(user)
                .shopName("John's Shop")
                .address("123 Main St")
                .build();

        Mockito.when(retailerProfileService.getProfile(1L)).thenReturn(Optional.of(profile));

        mvc.perform(get("/api/v1/retailers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.shopName").value("John's Shop"));
    }

    @Test
    void updateProfile_success() throws Exception {
        User user = User.builder().id(1L).fullName("John").phone("555").build();
        RetailerProfile profile = RetailerProfile.builder()
                .id(1L)
                .user(user)
                .shopName("Updated Shop")
                .address("456 New St")
                .build();

        Mockito.when(retailerProfileService.updateProfile(
                Mockito.eq(1L),
                Mockito.eq("Updated Shop"),
                Mockito.anyString(),
                Mockito.anyDouble(),
                Mockito.anyDouble(),
                Mockito.anyString()
        )).thenReturn(profile);

        mvc.perform(put("/api/v1/retailers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"shopName":"Updated Shop","address":"456 New St"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shopName").value("Updated Shop"));
    }
}
