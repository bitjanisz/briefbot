package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CaseStudyUpdateRequest {
    private String description;
    private String results;
    private List<Service> services;

    @Data
    @Builder
    public static class Service {
        private Long serviceId;
        private BigDecimal discountPercentage;
    }
}
