package com.admeliora.briefbot.adapter.in.web.companyprofile.mapper;

import com.admeliora.briefbot.adapter.in.web.companyprofile.model.response.CompanyProfileResponse;
import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;

public class CompanyProfileMapper {
    public static CompanyProfileResponse toResponse(CompanyProfile profile) {
        if (profile == null) return null;
        return new CompanyProfileResponse(
                profile.getId(),
                profile.getAccountId(),
                profile.getCompanyLegalName(),
                profile.getTaxId(),
                profile.getAddressLine(),
                profile.getContactEmail(),
                profile.getContactPhone(),
                profile.getShortValueProposition(),
                profile.getCoreValues(),
                profile.getAiToneStyle(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}

