package com.admeliora.briefbot.application.companyprofile.service;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.ListCompanyProfilesPort;
import com.admeliora.briefbot.application.companyprofile.port.out.CompanyProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCompanyProfilesService implements ListCompanyProfilesPort {

    private final CompanyProfilePort companyProfilePort;

    @Override
    @Transactional(readOnly = true)
    public List<CompanyProfile> listByAccountId(Long accountId) {
        return companyProfilePort.findAll();
    }
}

