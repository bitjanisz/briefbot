package com.admeliora.briefbot.application.companyprofile.port.in;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;

import java.util.List;

public interface ListCompanyProfilesPort {
    List<CompanyProfile> listByAccountId(Long accountId);
}

