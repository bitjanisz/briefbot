package com.admeliora.briefbot.adapter.out.persistence.offer.jpa;

import com.admeliora.briefbot.application.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepositoryJpa extends JpaRepository<Offer, Long> {
}

