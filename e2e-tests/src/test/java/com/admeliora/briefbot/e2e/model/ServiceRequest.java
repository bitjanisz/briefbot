package com.admeliora.briefbot.e2e.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceRequest {
    private String name;
    private String description;
    private Long accountId;
    private BigDecimal basePrice;
    private BigDecimal vatRate;
    private String currency;
    private String pricingUnit;
    private BigDecimal minPriceThreshold;
    private Boolean isActive;

    @Builder.Default
    private List<ServiceRelation> relations = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceRelation {
        private Long relatedServiceId;
        private String relationType;
        private String impactDescription;
    }
}

