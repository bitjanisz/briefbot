package com.admeliora.briefbot.application.companyprofile.port.in;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.command.CreateCompanyProfileCommand;

public interface CreateCompanyProfilePort {
    CompanyProfile create(CreateCompanyProfileCommand command);
}

