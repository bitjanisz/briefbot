package com.admeliora.briefbot.adapter.in.web.user.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Long id,
        String givenName,
        String familyName,
        String email,
        String picture,
        LocalDateTime lastLogin
) {
}