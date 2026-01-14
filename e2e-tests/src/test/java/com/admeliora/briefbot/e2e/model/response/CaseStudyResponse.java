package com.admeliora.briefbot.e2e.model.response;

import com.admeliora.briefbot.e2e.model.CaseStudyStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseStudyResponse {
    private Long id;
    private Long accountId;
    private String projectName;
    private String clientIndustry;
    private String keywords;
    private String scopeSummary;
    private String challengesSolved;
    private String budgetRangeEnum;
    private CaseStudyStatus status;
    private LocalDateTime createdAt;
    private List<Service> services;

    @Data
    public static class Service {
        private Long id;
        private Long serviceId;
        private BigDecimal discountPercentage;
        private LocalDateTime createdAt;
    }
}
