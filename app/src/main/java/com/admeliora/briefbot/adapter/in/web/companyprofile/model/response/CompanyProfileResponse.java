package com.admeliora.briefbot.adapter.in.web.companyprofile.model.response;

import java.time.LocalDateTime;

public record CompanyProfileResponse(
        Long id,
        Long accountId,
        String companyLegalName,
        String taxId,
        String addressLine,
        String contactEmail,
        String contactPhone,
        String shortValueProposition,
        String coreValues,
        String aiToneStyle,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

