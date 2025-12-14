package com.admeliora.briefbot.adapter.out.persistence.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepositoryJpa extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByAccountId(Long accountId);
}

