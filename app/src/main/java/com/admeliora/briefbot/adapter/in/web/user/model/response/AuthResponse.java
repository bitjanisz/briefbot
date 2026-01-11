package com.admeliora.briefbot.adapter.in.web.user.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Response DTO for authentication (login/registration)
 */
@Builder
@Schema(description = "Authentication response with user details")
public record AuthResponse(

        @Schema(description = "User ID")
        Long id,

        @Schema(description = "User email")
        String email,

        @Schema(description = "User first name")
        String givenName,

        @Schema(description = "User last name")
        String familyName,

        @Schema(description = "Success message")
        String message
) {
}

