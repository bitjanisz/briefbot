package com.admeliora.briefbot.infrastructure.adapter.in.web.service.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceUpdateRequest(
        @NotNull Long id,
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 10000)
        String description
) {
}