package com.admeliora.briefbot.adapter.in.web.briefing.model.response;

import java.time.LocalDateTime;
public record BriefingVersionResponse(
        Long id,
        Long briefingId,
        Integer versionNumber,
        String formStructure,
        String clientResponses,
        Long createdByUserId,
        LocalDateTime createdAt
        ) {
}
