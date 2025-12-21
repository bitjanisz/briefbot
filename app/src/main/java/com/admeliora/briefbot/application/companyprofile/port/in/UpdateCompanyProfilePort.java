package com.admeliora.briefbot.application.companyprofile.port.in;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.command.UpdateCompanyProfileCommand;

public interface UpdateCompanyProfilePort {
    CompanyProfile update(UpdateCompanyProfileCommand command);
}

