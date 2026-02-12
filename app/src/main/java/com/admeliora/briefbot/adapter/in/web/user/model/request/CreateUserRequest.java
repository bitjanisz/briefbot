package com.admeliora.briefbot.adapter.in.web.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(max = 100)
        String givenName,
        @NotBlank
        @Size(max = 100)
        String familyName
) {
}
