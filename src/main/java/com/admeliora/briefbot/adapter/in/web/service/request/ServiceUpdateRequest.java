package com.admeliora.briefbot.adapter.in.web.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ServiceUpdateRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 10000)
        String description
) {
}