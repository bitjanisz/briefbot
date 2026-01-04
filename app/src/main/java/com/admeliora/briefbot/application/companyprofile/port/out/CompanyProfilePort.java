package com.admeliora.briefbot.application.companyprofile.port.out;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;

import java.util.List;
import java.util.Optional;

public interface CompanyProfilePort {
    CompanyProfile save(CompanyProfile profile);

    Optional<CompanyProfile> findById(Long id);

    List<CompanyProfile> findAll();

    boolean existsById(Long id);

    CompanyProfile getReferenceById(Long id);

    void delete(CompanyProfile profile);
}

