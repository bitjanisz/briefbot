package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BriefingVersionResponse {
    private Long id;
    private Long briefingId;
    private Integer versionNumber;
    private String clientResponses;
    private Boolean finalized;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
