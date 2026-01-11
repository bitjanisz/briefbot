package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ServiceRequest {
    private String name;
    private String description;
    private Long accountId;
    private BigDecimal basePrice;
    private BigDecimal vatRate;
    private String currency;
    private String pricingUnit;
    private Boolean isActive;
    private BigDecimal minPriceThreshold;
//    private List<ServiceRelation> relations;
//
//    @Data
//    @Builder
//    public static class ServiceRelation {
//        private Long relatedServiceId;
//        private String relationType;
//        private String impactDescription;
//    }
}
