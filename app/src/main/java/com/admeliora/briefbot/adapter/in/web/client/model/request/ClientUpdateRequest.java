package com.admeliora.briefbot.adapter.in.web.client.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientUpdateRequest(
        @NotNull
        Long id,
        @NotBlank
        @Size(max = 150)
        String name,
        @NotBlank
        @Email
        @Size(max = 150)
        String email,
        @Size(max = 255)
        String companyName,
        @Size(max = 100)
        String industry
) {
}

