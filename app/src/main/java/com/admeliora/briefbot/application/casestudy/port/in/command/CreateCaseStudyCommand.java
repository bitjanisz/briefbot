package com.admeliora.briefbot.application.casestudy.port.in.command;

import java.util.List;

public record CreateCaseStudyCommand(
        String projectName,
        String clientIndustry,
        String keywords,
        String scopeSummary,
        String challengesSolved,
        String budgetRangeEnum,
        List<CaseStudyServiceCommand> services
) {
}
