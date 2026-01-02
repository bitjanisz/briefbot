package com.admeliora.briefbot.e2e.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseStudyResponse {
    private Long id;
    private String title;
    private String description;
    private String results;
    private String technologies;
    private Boolean published;
    private LocalDateTime createdAt;
}
