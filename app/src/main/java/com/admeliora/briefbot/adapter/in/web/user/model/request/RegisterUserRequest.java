package com.admeliora.briefbot.adapter.in.web.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Request DTO for user registration
 */
@Builder
@Schema(description = "User registration request")
public record RegisterUserRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Schema(description = "User email address", example = "user@example.com")
        String email,

        @NotBlank(message = "Given name is required")
        @Size(min = 1, max = 100, message = "Given name must be between 1 and 100 characters")
        @Schema(description = "User first name", example = "John")
        String givenName,

        @NotBlank(message = "Family name is required")
        @Size(min = 1, max = 100, message = "Family name must be between 1 and 100 characters")
        @Schema(description = "User last name", example = "Doe")
        String familyName
) {
}

