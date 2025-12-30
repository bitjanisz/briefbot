package com.admeliora.briefbot.adapter.out.persistence.client.jpa;

import com.admeliora.briefbot.application.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepositoryJpa extends JpaRepository<Client, Long> {
}

