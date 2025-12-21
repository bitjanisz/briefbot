package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.casestudy.port.in.CreateCaseStudyPort;
import com.admeliora.briefbot.application.casestudy.port.in.command.CreateCaseStudyCommand;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCaseStudyService implements CreateCaseStudyPort {

    private final CaseStudyPort caseStudyPort;
    private final AccountPort accountPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Transactional
    public CaseStudy create(CreateCaseStudyCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        if (!accountPort.existsById(accountFilterContext.getAccountId())) {
            throw new EntityNotFoundException("Account not found: " + accountFilterContext.getAccountId());
        }

        CaseStudy caseStudy = CaseStudy.builder()
                .accountId(accountFilterContext.getAccountId())
                .projectName(command.projectName())
                .clientIndustry(command.clientIndustry())
                .keywords(command.keywords())
                .scopeSummary(command.scopeSummary())
                .challengesSolved(command.challengesSolved())
                .budgetRangeEnum(command.budgetRangeEnum())
                .isPublic(command.isPublic())
                .build();

        return caseStudyPort.save(caseStudy);
    }
}

