package com.admeliora.briefbot.e2e.model.response;

import lombok.Builder;

/**
 * Response DTO for authentication (login/registration)
 */
@Builder
public record AuthResponse(

        Long id,

        String email,

        String givenName,

        String familyName,

        String message
) {
}
