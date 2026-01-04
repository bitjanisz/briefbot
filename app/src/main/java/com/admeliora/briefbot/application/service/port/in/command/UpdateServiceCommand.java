package com.admeliora.briefbot.application.service.port.in.command;

import java.math.BigDecimal;
import java.util.List;

public record UpdateServiceCommand(
        Long id,
        String name,
        String description,
        BigDecimal basePrice,
        BigDecimal vatRate,
        String currency,
        String pricingUnit,
        BigDecimal minPriceThreshold,
        Boolean isActive,
        List<ServiceRelation> relations
) {
}

