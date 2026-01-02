package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OfferRequest {
    private String title;
    private String description;
    private Long clientId;
    private String status;
    private LocalDateTime validUntil;
}
