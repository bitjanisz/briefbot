package com.admeliora.briefbot.application.companyprofile.service;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.common.exception.NoAccountAssignedException;
import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.CreateCompanyProfilePort;
import com.admeliora.briefbot.application.companyprofile.port.in.command.CreateCompanyProfileCommand;
import com.admeliora.briefbot.application.companyprofile.port.out.CompanyProfilePort;
import com.admeliora.briefbot.infrastructure.context.AccountFilterContext;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCompanyProfileService implements CreateCompanyProfilePort {

    private final CompanyProfilePort companyProfilePort;
    private final AccountPort accountPort;
    private final AccountFilterContext accountFilterContext;

    @Override
    @Transactional
    public CompanyProfile create(CreateCompanyProfileCommand command) {
        if (accountFilterContext.getAccountId() == null) {
            throw new NoAccountAssignedException(accountFilterContext.getUserEmail());
        }
        if (!accountPort.existsById(accountFilterContext.getAccountId())) {
            throw new EntityNotFoundException("Account not found: " + accountFilterContext.getAccountId());
        }

        CompanyProfile profile = CompanyProfile.builder()
                .accountId(accountFilterContext.getAccountId())
                .companyLegalName(command.companyLegalName())
                .taxId(command.taxId())
                .addressLine(command.addressLine())
                .contactEmail(command.contactEmail())
                .contactPhone(command.contactPhone())
                .shortValueProposition(command.shortValueProposition())
                .coreValues(command.coreValues())
                .aiToneStyle(command.aiToneStyle())
                .build();

        return companyProfilePort.save(profile);
    }
}

