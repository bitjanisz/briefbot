package com.admeliora.briefbot.adapter.in.web.companyprofile.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompanyProfileCreateRequest(
        @Size(max = 255)
        String companyLegalName,
        @Size(max = 50)
        String taxId,
        String addressLine,
        @Email
        @Size(max = 150)
        String contactEmail,
        @Size(max = 50)
        String contactPhone,
        String shortValueProposition,
        String coreValues,
        @Size(max = 100)
        String aiToneStyle
) {
}

