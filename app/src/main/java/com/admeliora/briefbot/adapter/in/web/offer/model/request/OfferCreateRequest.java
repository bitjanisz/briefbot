package com.admeliora.briefbot.adapter.in.web.offer.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OfferCreateRequest(
        @NotNull
        Long clientId,
        Long briefingId,
        @NotBlank
        @Size(max = 50)
        String currentStatus
) {
}

