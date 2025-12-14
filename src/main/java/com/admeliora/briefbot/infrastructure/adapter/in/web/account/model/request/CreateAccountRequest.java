package com.admeliora.briefbot.infrastructure.adapter.in.web.account.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        Long ownerId
) {
}