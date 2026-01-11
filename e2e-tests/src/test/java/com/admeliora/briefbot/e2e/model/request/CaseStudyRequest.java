package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CaseStudyRequest {
    private String projectName;
    private String description;
    private String results;
    private String technologies;
    private List<Service> services;

    @Data
    @Builder
    public static class Service {
        private Long serviceId;
        private BigDecimal discountPercentage;
    }
}
