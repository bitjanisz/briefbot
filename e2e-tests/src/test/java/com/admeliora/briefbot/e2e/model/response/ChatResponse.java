package com.admeliora.briefbot.e2e.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponse {
    private String response;
    private List<SuggestedService> suggestedServices;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SuggestedService {
        private Long serviceId;
        private String serviceName;
        private BigDecimal discountPercentage;
        private BigDecimal finalPrice;
    }
}
