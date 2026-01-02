package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderRequest {
    private Long offerId;
    private String notes;
}
