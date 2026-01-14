package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CaseStudyRequest {
    private String projectName;
    private String clientIndustry;
    private String keywords;
    private String scopeSummary;
    private String challengesSolved;
    private String budgetRangeEnum;
    private List<Service> services;

    @Data
    @Builder
    public static class Service {
        private Long serviceId;
        private BigDecimal discountPercentage;
    }
}
