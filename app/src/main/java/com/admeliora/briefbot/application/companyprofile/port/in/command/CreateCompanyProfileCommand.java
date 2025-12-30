package com.admeliora.briefbot.application.companyprofile.port.in.command;

public record CreateCompanyProfileCommand(
        String companyLegalName,
        String taxId,
        String addressLine,
        String contactEmail,
        String contactPhone,
        String shortValueProposition,
        String coreValues,
        String aiToneStyle
) {
}

