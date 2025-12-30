package com.admeliora.briefbot.application.casestudy.port.in.command;

public record CreateCaseStudyCommand(
        String projectName,
        String clientIndustry,
        String keywords,
        String scopeSummary,
        String challengesSolved,
        String budgetRangeEnum,
        Boolean isPublic
) {
}

