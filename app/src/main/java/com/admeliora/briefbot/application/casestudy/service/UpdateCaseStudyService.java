package com.admeliora.briefbot.application.casestudy.service;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.model.CaseStudyService;
import com.admeliora.briefbot.application.casestudy.port.in.UpdateCaseStudyPort;
import com.admeliora.briefbot.application.casestudy.port.in.command.UpdateCaseStudyCommand;
import com.admeliora.briefbot.application.casestudy.port.out.CaseStudyPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        caseStudy.setStatus(caseStudy.getStatus());

        // Update services with discounts
        if (command.services() != null) {
            // Clear existing services
            if (caseStudy.getCaseStudyServices() != null) {
                caseStudy.getCaseStudyServices().clear();
            } else {
                caseStudy.setCaseStudyServices(new HashSet<>());
            }

            // Add new services
            Set<CaseStudyService> newServices = command.services().stream()
                    .map(serviceCmd -> CaseStudyService.builder()
                            .caseStudy(caseStudy)
                            .serviceId(serviceCmd.serviceId())
                            .discountPercentage(serviceCmd.discountPercentage())
                            .build())
                    .collect(Collectors.toSet());
            caseStudy.getCaseStudyServices().addAll(newServices);
        }

        return caseStudyPort.save(caseStudy);
    }
}

