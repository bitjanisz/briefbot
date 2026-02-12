package com.admeliora.briefbot.application.casestudy.port.in.command;

import java.math.BigDecimal;

public record CaseStudyServiceCommand(
        Long serviceId,
        BigDecimal discountPercentage
) {
}

