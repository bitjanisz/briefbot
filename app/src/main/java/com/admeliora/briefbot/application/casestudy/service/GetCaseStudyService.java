package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.in.GetCaseStudyPort;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCaseStudyService implements GetCaseStudyPort {

    private final CaseStudyPort caseStudyPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<CaseStudy> getById(Long id) {
        return caseStudyPort.findById(id);
    }
}

