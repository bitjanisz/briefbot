package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OfferVersionItemRequest {
    private Long offerVersionId;
    private Long originalServiceId;
    private String serviceName;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal vatRate;
}
