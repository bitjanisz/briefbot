package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.in.ListCaseStudiesPort;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCaseStudiesService implements ListCaseStudiesPort {

    private final CaseStudyPort caseStudyPort;

    @Override
    @Transactional(readOnly = true)
    public List<CaseStudy> listByAccountId(Long accountId) {
        return caseStudyPort.findAll();
    }
}

