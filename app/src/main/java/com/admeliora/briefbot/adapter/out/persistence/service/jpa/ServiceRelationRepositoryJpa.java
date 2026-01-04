package com.admeliora.briefbot.adapter.out.persistence.service.jpa;

import com.admeliora.briefbot.application.service.model.ServiceRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRelationRepositoryJpa extends JpaRepository<ServiceRelation, Long> {
    List<ServiceRelation> findByParentService_Id(Long parentServiceId);
}

