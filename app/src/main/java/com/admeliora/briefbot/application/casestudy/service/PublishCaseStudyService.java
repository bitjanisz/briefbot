package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.model.CaseStudyStatus;
import com.admeliora.briefbot.application.casestudy.port.in.PublishCaseStudyPort;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PublishCaseStudyService implements PublishCaseStudyPort {

    private final CaseStudyPort caseStudyPort;

    @Override
    @Transactional
    public CaseStudy publish(Long id) {
        CaseStudy caseStudy = caseStudyPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Case study not found: " + id));

        // TODO: Add validation for completeness (e.g., has results, etc.)

        caseStudy.setStatus(CaseStudyStatus.PUBLISHED);
        return caseStudyPort.save(caseStudy);
    }
}
