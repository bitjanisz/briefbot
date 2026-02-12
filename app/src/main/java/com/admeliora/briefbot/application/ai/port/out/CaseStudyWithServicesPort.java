package com.admeliora.briefbot.application.ai.port.out;

import com.admeliora.briefbot.application.ai.model.CaseStudyWithServicesDto;

import java.util.List;
import java.util.Optional;

public interface CaseStudyWithServicesPort {
    Optional<CaseStudyWithServicesDto> getCaseStudyWithServices(Long caseStudyId);
    List<CaseStudyWithServicesDto> getAllCaseStudiesWithServices();
}

