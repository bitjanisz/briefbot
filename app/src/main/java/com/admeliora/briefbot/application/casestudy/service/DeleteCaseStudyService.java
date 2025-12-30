package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.in.DeleteCaseStudyPort;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCaseStudyService implements DeleteCaseStudyPort {

    private final CaseStudyPort caseStudyPort;

    @Override
    @Transactional
    public void delete(Long id) {
        CaseStudy caseStudy = caseStudyPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CaseStudy not found: " + id));
        caseStudyPort.delete(caseStudy);
    }
}

