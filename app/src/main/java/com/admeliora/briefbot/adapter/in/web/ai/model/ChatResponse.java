package com.admeliora.briefbot.adapter.in.web.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AI chat response")
public record ChatResponse(
        @Schema(description = "AI assistant response", example = "Here are the available case studies...")
        String response
) {
}

