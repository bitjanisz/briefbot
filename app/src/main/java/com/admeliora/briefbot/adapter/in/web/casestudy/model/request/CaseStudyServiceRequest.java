package com.admeliora.briefbot.adapter.in.web.casestudy.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CaseStudyServiceRequest(
        @NotNull
        Long serviceId,

        @DecimalMin(value = "0.00")
        @DecimalMax(value = "100.00")
        BigDecimal discountPercentage
) {
}
