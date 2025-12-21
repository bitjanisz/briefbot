package com.admeliora.briefbot.adapter.in.web.briefing.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record BriefingResponse(
        Long id,
        Long accountId,
        Long clientId,
        String status,
        LocalDateTime createdAt,
        List<BriefingVersionResponse> versions
) {
}

