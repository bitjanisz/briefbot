package com.admeliora.briefbot.application.companyprofile.port.in.command;

public record UpdateCompanyProfileCommand(
        Long id,
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

