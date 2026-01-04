package com.admeliora.briefbot.adapter.in.web.briefing.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BriefingCreateRequest(
        @NotNull
        Long clientId,
        @Size(max = 50)
        String status
) {
}

