package com.admeliora.briefbot.application.casestudy.port.out;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;

import java.util.List;
import java.util.Optional;

public interface CaseStudyPort {
    void delete(CaseStudy caseStudy);

    CaseStudy getReferenceById(Long id);

    boolean existsById(Long id);

    List<CaseStudy> findAll();

    Optional<CaseStudy> findById(Long id);

    CaseStudy save(CaseStudy caseStudy);
}

