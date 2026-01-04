package com.admeliora.briefbot.application.companyprofile.service;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.GetCompanyProfilePort;
import com.admeliora.briefbot.application.companyprofile.port.out.CompanyProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCompanyProfileService implements GetCompanyProfilePort {

    private final CompanyProfilePort companyProfilePort;

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyProfile> getById(Long id) {
        return companyProfilePort.findById(id);
    }
}

