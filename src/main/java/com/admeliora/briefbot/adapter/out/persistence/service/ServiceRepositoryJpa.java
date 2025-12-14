package com.admeliora.briefbot.adapter.out.persistence.service;

import com.admeliora.briefbot.domain.service.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepositoryJpa extends JpaRepository<Service, Long> {
    List<Service> findByAccountId(Long accountId);
}

