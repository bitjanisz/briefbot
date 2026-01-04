package com.admeliora.briefbot.adapter.in.web.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Request DTO for user login
 */
@Builder
@Schema(description = "User login request")
public record LoginRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Schema(description = "User email address", example = "user@example.com")
        String email,

        @NotBlank(message = "Password is required")
        @Schema(description = "User password", example = "password123")
        String password
) {
}

