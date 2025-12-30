package com.admeliora.briefbot.adapter.in.web.order.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record OrderCreateRequest(
        @NotNull
        Long offerVersionId,
        @NotBlank
        @Size(max = 50)
        String contractStatus,
        @Size(max = 255)
        String contractFileUrl,
        LocalDateTime signedAt
) {
}

