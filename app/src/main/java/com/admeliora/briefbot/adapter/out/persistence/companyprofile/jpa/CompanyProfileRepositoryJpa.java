package com.admeliora.briefbot.adapter.out.persistence.companyprofile.jpa;

import com.admeliora.briefbot.application.companyprofile.model.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProfileRepositoryJpa extends JpaRepository<CompanyProfile, Long> {
}

