package com.admeliora.briefbot.e2e.model.response;

import com.admeliora.briefbot.e2e.model.CaseStudyStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseStudyResponse {
    private Long id;
    private String projectName;
    private String description;
    private String results;
    private String technologies;
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
