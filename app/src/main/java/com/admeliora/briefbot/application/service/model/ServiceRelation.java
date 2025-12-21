package com.admeliora.briefbot.application.service.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * ServiceRelation - Child entity of Service aggregate (DDD)
 * Part of Service aggregate, maintains parent reference
 * References related service by ID only (DDD principle)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "service_relations")
public class ServiceRelation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_service_id", nullable = false)
    private Service parentService;

    @Column(name = "related_service_id", nullable = false)
    private Long relatedServiceId;

    @Column(name = "relation_type", nullable = false, length = 50)
    private String relationType;

    @Column(name = "impact_description", length = 255)
    private String impactDescription;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

