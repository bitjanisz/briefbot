package com.admeliora.briefbot.adapter.in.web.order.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record OrderUpdateRequest(
        @NotNull
        Long id,
        @NotBlank
        @Size(max = 50)
        String contractStatus,
        @Size(max = 255)
        String contractFileUrl,
        LocalDateTime signedAt
) {
}

