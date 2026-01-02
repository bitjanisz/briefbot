package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BriefingRequest {
    private String title;
    private String description;
    private Long clientId;
    private LocalDateTime deadline;
}
