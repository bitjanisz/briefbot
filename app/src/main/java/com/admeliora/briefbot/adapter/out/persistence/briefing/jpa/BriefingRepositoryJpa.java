package com.admeliora.briefbot.adapter.out.persistence.briefing.jpa;

import com.admeliora.briefbot.application.briefing.model.Briefing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefingRepositoryJpa extends JpaRepository<Briefing, Long> {
}

