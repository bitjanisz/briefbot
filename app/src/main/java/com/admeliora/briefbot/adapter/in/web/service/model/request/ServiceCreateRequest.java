package com.admeliora.briefbot.adapter.in.web.service.model.request;

import com.admeliora.briefbot.adapter.in.web.service.model.ServiceRelation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ServiceCreateRequest(
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 10000)
        String description,
        @NotNull
        BigDecimal basePrice,
        BigDecimal vatRate,
        String currency,
        @NotBlank
        String pricingUnit,
        BigDecimal minPriceThreshold,
        Boolean isActive,
        List<ServiceRelation> relations
) {
}