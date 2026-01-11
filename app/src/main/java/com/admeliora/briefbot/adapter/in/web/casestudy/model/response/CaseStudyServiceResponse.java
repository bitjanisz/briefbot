package com.admeliora.briefbot.adapter.in.web.casestudy.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CaseStudyServiceResponse(
        Long id,
        Long serviceId,
        BigDecimal discountPercentage,
        LocalDateTime createdAt
) {
}
