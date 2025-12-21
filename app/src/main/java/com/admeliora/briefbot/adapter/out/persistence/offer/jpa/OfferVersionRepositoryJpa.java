package com.admeliora.briefbot.adapter.out.persistence.offer.jpa;

import com.admeliora.briefbot.application.offer.model.OfferVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferVersionRepositoryJpa extends JpaRepository<OfferVersion, Long> {
}

