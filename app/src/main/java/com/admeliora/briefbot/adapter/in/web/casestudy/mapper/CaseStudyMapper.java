package com.admeliora.briefbot.adapter.in.web.casestudy.mapper;

import com.admeliora.briefbot.adapter.in.web.casestudy.model.response.CaseStudyResponse;
import com.admeliora.briefbot.application.casestudy.model.CaseStudy;

public class CaseStudyMapper {
    public static CaseStudyResponse toResponse(CaseStudy caseStudy) {
        if (caseStudy == null) return null;
        return new CaseStudyResponse(
                caseStudy.getId(),
                caseStudy.getAccountId(),
                caseStudy.getProjectName(),
                caseStudy.getClientIndustry(),
                caseStudy.getKeywords(),
                caseStudy.getScopeSummary(),
                caseStudy.getChallengesSolved(),
                caseStudy.getBudgetRangeEnum(),
                caseStudy.getIsPublic(),
                caseStudy.getCreatedAt()
        );
    }
}

