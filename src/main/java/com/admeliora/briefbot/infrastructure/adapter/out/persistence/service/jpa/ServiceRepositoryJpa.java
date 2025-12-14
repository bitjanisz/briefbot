package com.admeliora.briefbot.infrastructure.adapter.out.persistence.service.jpa;

import com.admeliora.briefbot.service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepositoryJpa extends JpaRepository<Service, Long> {
    List<Service> findByAccountId(Long accountId);
}

