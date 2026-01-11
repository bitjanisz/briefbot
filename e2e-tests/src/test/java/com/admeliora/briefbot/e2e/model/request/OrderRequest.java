package com.admeliora.briefbot.e2e.model.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderRequest {
    private Long offerId;
    private String notes;
}
