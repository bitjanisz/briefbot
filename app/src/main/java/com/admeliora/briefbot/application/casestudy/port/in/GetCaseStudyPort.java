package com.admeliora.briefbot.application.casestudy.port.in;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;

import java.util.Optional;

public interface GetCaseStudyPort {
    Optional<CaseStudy> getById(Long id);
}

