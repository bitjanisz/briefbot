package com.admeliora.briefbot.adapter.out.persistence.companyprofile;

import com.admeliora.briefbot.adapter.out.persistence.companyprofile.jpa.CompanyProfileRepositoryJpa;
import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import com.admeliora.briefbot.application.companyprofile.port.out.CompanyProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyProfileAdapter implements CompanyProfilePort {

    private final CompanyProfileRepositoryJpa companyProfileRepository;

    @Override
    public CompanyProfile save(CompanyProfile profile) {
        return companyProfileRepository.save(profile);
    }

    @Override
    public Optional<CompanyProfile> findById(Long id) {
        return companyProfileRepository.findById(id);
    }

    @Override
    public List<CompanyProfile> findAll() {
        return companyProfileRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return companyProfileRepository.existsById(id);
    }

    @Override
    public CompanyProfile getReferenceById(Long id) {
        return companyProfileRepository.getReferenceById(id);
    }

    @Override
    public void delete(CompanyProfile profile) {
        companyProfileRepository.delete(profile);
    }
}

