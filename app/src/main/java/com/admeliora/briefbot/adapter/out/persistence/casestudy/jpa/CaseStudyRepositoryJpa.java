package com.admeliora.briefbot.adapter.out.persistence.casestudy.jpa;

import com.admeliora.briefbot.application.casestudy.model.CaseStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseStudyRepositoryJpa extends JpaRepository<CaseStudy, Long> {
}

