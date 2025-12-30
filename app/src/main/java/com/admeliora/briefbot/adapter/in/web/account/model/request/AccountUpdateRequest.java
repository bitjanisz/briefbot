package com.admeliora.briefbot.adapter.in.web.account.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountUpdateRequest(
        @NotNull
        Long id,
        @NotBlank
        @Size(max = 100)
        String name
) {
}

