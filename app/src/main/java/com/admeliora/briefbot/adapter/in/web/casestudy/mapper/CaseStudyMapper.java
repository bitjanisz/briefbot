package com.admeliora.briefbot.adapter.in.web.casestudy.mapper;

import com.admeliora.briefbot.adapter.in.web.casestudy.model.request.CaseStudyCreateRequest;
import com.admeliora.briefbot.adapter.in.web.casestudy.model.request.CaseStudyUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.casestudy.model.response.CaseStudyResponse;
import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import com.admeliora.briefbot.application.casestudy.model.CaseStudyService;
import com.admeliora.briefbot.application.casestudy.port.in.command.CaseStudyServiceCommand;
import com.admeliora.briefbot.application.casestudy.port.in.command.CreateCaseStudyCommand;
import com.admeliora.briefbot.application.casestudy.port.in.command.UpdateCaseStudyCommand;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CaseStudyMapper {

    public static CaseStudyResponse toResponse(CaseStudy caseStudy) {
        if (caseStudy == null) return null;

        List<CaseStudyResponse.Service> services = caseStudy.getCaseStudyServices() != null ?
                caseStudy.getCaseStudyServices().stream()
                        .map(CaseStudyMapper::toServiceResponse)
                        .collect(Collectors.toList()) : Collections.emptyList();

        return new CaseStudyResponse(
                caseStudy.getId(),
                caseStudy.getAccountId(),
                caseStudy.getProjectName(),
                caseStudy.getClientIndustry(),
                caseStudy.getKeywords(),
                caseStudy.getScopeSummary(),
                caseStudy.getChallengesSolved(),
                caseStudy.getBudgetRangeEnum(),
                caseStudy.getStatus(),
                caseStudy.getCreatedAt(),
                services
        );
    }

    public static CaseStudyResponse.Service toServiceResponse(CaseStudyService service) {
        if (service == null) return null;
        return new CaseStudyResponse.Service(
                service.getId(),
                service.getServiceId(),
                service.getDiscountPercentage(),
                service.getCreatedAt()
        );
    }

    public static CreateCaseStudyCommand toCreateCommand(CaseStudyCreateRequest request) {
        List<CaseStudyServiceCommand> services = request.services() != null ?
                request.services().stream()
                        .map(s -> new CaseStudyServiceCommand(s.serviceId(), s.discountPercentage()))
                        .collect(Collectors.toList()) : null;

        return new CreateCaseStudyCommand(
                request.projectName(),
                request.clientIndustry(),
                request.keywords(),
                request.scopeSummary(),
                request.challengesSolved(),
                request.budgetRangeEnum(),
                services
        );
    }

    public static UpdateCaseStudyCommand toUpdateCommand(CaseStudyUpdateRequest request) {
        List<CaseStudyServiceCommand> services = request.services() != null ?
                request.services().stream()
                        .map(s -> new CaseStudyServiceCommand(s.serviceId(), s.discountPercentage()))
                        .collect(Collectors.toList()) : null;

        return new UpdateCaseStudyCommand(
                request.id(),
                request.projectName(),
                request.clientIndustry(),
                request.keywords(),
                request.scopeSummary(),
                request.challengesSolved(),
                request.budgetRangeEnum(),
                services
        );
    }
}
