package com.admeliora.briefbot.adapter.in.web.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "AI chat response")
public record ChatResponse(
        @Schema(description = "AI assistant response", example = "Here are the available case studies...")
        String response,

        @Schema(description = "Suggested services based on case studies")
        List<SuggestedService> suggestedServices
) {
    public record SuggestedService(
            @Schema(description = "Service ID")
            Long serviceId,

            @Schema(description = "Service name")
            String serviceName,

            @Schema(description = "Discount percentage")
            BigDecimal discountPercentage,

            @Schema(description = "Final price after discount")
            BigDecimal finalPrice
    ) {
    }
}
