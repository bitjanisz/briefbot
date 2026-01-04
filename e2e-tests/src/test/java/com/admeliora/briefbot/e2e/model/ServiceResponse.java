package com.admeliora.briefbot.e2e.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResponse {
    private Long id;
    private Long accountId;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private BigDecimal vatRate;
    private String currency;
    private String pricingUnit;
    private BigDecimal minPriceThreshold;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RelatedService> relatedServices;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelatedService {
        private Long id;
        private Long relatedServiceId;
        private String relationType;
        private String impactDescription;
    }
}

