package com.admeliora.briefbot.adapter.in.web.service.model.response;

import com.admeliora.briefbot.adapter.in.web.service.model.ServiceRelation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceResponse(
        Long id,
        String name,
        String description,
        Long accountId,
        BigDecimal basePrice,
        BigDecimal vatRate,
        String currency,
        String pricingUnit,
        BigDecimal minPriceThreshold,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long version
//        List<ServiceRelation> relations
) {
}
