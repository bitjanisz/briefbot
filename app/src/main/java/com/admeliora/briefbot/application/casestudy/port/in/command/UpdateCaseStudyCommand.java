package com.admeliora.briefbot.application.casestudy.port.in.command;

public record UpdateCaseStudyCommand(
        Long id,
        String projectName,
        String clientIndustry,
        String keywords,
        String scopeSummary,
        String challengesSolved,
        String budgetRangeEnum,
        Boolean isPublic
) {
}

