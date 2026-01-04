package com.admeliora.briefbot.application.companyprofile.port.in;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;

import java.util.Optional;

public interface GetCompanyProfilePort {
    Optional<CompanyProfile> getById(Long id);
}

