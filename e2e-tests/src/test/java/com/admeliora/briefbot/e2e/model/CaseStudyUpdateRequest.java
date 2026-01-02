package com.admeliora.briefbot.e2e.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaseStudyUpdateRequest {
    private String description;
    private String results;
}
