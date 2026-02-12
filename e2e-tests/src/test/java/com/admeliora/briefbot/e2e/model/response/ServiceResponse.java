package com.admeliora.briefbot.e2e.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServiceResponse {
    private Long id;
    private String name;
    private String description;
    private Long accountId;
    private BigDecimal basePrice;
    private BigDecimal vatRate;
    private String currency;
    private String pricingUnit;
    private Boolean isActive;
    private BigDecimal minPriceThreshold;
    private Long version;
//    private List<ServiceRelationResponse> relatedServices;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    @Data
//    public static class ServiceRelationResponse {
//        private Long id;
//        private Long relatedServiceId;
//        private String relationType;
//        private String impactDescription;
//        private LocalDateTime createdAt;
//    }
}
