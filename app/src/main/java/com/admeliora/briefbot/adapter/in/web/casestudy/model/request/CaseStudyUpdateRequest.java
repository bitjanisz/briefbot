package com.admeliora.briefbot.adapter.in.web.casestudy.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record CaseStudyUpdateRequest(
        @NotNull
        Long id,
        @NotBlank
        @Size(max = 255)
        String projectName,
        @Size(max = 100)
        String clientIndustry,
        String keywords,
        String scopeSummary,
        String challengesSolved,
        @Size(max = 50)
        String budgetRangeEnum,
        @Valid
        List<Service> services
) {
    public record Service(
            @NotNull
            Long serviceId,
            BigDecimal discountPercentage
    ) {
    }
}
