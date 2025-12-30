package com.admeliora.briefbot.adapter.in.web.briefing.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BriefingUpdateRequest(
        @NotNull
        Long id,
        @Size(max = 50)
        String status
) {
}

