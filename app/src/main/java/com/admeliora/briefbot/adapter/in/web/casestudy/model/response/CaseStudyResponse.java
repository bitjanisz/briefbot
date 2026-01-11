package com.admeliora.briefbot.adapter.in.web.casestudy.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record CaseStudyResponse(
        Long id,
        Long accountId,
        String projectName,
        String clientIndustry,
        String keywords,
        String scopeSummary,
        String challengesSolved,
        String budgetRangeEnum,
        Boolean isPublic,
        LocalDateTime createdAt,
        List<CaseStudyServiceResponse> services
) {
}

