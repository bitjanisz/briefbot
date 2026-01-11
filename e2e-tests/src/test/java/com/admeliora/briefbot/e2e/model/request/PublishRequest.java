package com.admeliora.briefbot.e2e.model.request;

import com.admeliora.briefbot.e2e.model.CaseStudyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublishRequest {
    private CaseStudyStatus status;
}
