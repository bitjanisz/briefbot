package com.admeliora.briefbot.e2e.model.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Long id,
        String givenName,
        String familyName,
        String email,
        String picture,
        LocalDateTime lastLogin,
        LocalDateTime createdAt
) {
}
