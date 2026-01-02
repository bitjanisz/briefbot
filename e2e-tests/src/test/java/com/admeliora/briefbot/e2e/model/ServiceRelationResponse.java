package com.admeliora.briefbot.e2e.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceRelationResponse {
    private Long id;
    private Long serviceId;
    private Long relatedServiceId;
    private String relationType;
    private String impactDescription;
    private LocalDateTime createdAt;
}
