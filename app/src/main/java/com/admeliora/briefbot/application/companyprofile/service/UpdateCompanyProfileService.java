package com.admeliora.briefbot.application.companyprofile.service;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.UpdateCompanyProfilePort;
import com.admeliora.briefbot.application.companyprofile.port.in.command.UpdateCompanyProfileCommand;
import com.admeliora.briefbot.application.companyprofile.port.out.CompanyProfilePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCompanyProfileService implements UpdateCompanyProfilePort {

    private final CompanyProfilePort companyProfilePort;

    @Override
    @Transactional
    public CompanyProfile update(UpdateCompanyProfileCommand command) {
        CompanyProfile profile = companyProfilePort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("CompanyProfile not found: " + command.id()));

        profile.setCompanyLegalName(command.companyLegalName());
        profile.setTaxId(command.taxId());
        profile.setAddressLine(command.addressLine());
        profile.setContactEmail(command.contactEmail());
        profile.setContactPhone(command.contactPhone());
        profile.setShortValueProposition(command.shortValueProposition());
        profile.setCoreValues(command.coreValues());
        profile.setAiToneStyle(command.aiToneStyle());

        return companyProfilePort.save(profile);
    }
}

