package com.admeliora.briefbot.e2e.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferResponse {
    private Long id;
    private String title;
    private String description;
    private Long clientId;
    private String status;
    private LocalDateTime validUntil;
    private LocalDateTime createdAt;
    private Integer versionCount;
}
