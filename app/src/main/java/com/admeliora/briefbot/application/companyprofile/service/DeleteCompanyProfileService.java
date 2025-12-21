package com.admeliora.briefbot.application.companyprofile.service;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.in.DeleteCompanyProfilePort;
import com.admeliora.briefbot.application.companyprofile.port.out.CompanyProfilePort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCompanyProfileService implements DeleteCompanyProfilePort {

    private final CompanyProfilePort companyProfilePort;

    @Override
    @Transactional
    public void delete(Long id) {
        CompanyProfile profile = companyProfilePort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CompanyProfile not found: " + id));
        companyProfilePort.delete(profile);
    }
}

