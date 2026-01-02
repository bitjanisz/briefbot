package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceRelationRequest {
    private Long serviceId;
    private Long relatedServiceId;
    private String relationType;
    private String impactDescription;
}
