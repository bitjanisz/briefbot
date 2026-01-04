package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.port.in.UpdateCaseStudyPort;
import com.admeliora.briefbot.application.casestudy.port.in.command.UpdateCaseStudyCommand;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCaseStudyService implements UpdateCaseStudyPort {

    private final CaseStudyPort caseStudyPort;

    @Override
    @Transactional
    public CaseStudy update(UpdateCaseStudyCommand command) {
        CaseStudy caseStudy = caseStudyPort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("CaseStudy not found: " + command.id()));

        caseStudy.setProjectName(command.projectName());
        caseStudy.setClientIndustry(command.clientIndustry());
        caseStudy.setKeywords(command.keywords());
        caseStudy.setScopeSummary(command.scopeSummary());
        caseStudy.setChallengesSolved(command.challengesSolved());
        caseStudy.setBudgetRangeEnum(command.budgetRangeEnum());
        caseStudy.setIsPublic(command.isPublic());

        return caseStudyPort.save(caseStudy);
    }
}

