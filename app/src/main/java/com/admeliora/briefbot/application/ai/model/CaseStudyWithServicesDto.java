package com.admeliora.briefbot.application.ai.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CaseStudyWithServicesDto(
        Long id,
        String projectName,
        String clientIndustry,
        String keywords,
        String scopeSummary,
        String challengesSolved,
        String budgetRangeEnum,
        Boolean isPublic,
        LocalDateTime createdAt,
        List<ServiceDto> services
) {
    public record ServiceDto(
            Long serviceId,
            String serviceName,
            String description,
            BigDecimal basePrice,
            String currency,
            String pricingUnit,
            BigDecimal discountPercentage,
            BigDecimal finalPrice
    ) {
    }
}

