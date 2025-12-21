package com.admeliora.briefbot.application.briefing.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * BriefingVersion - Child entity of Briefing aggregate (DDD)
 * Part of Briefing aggregate, maintains parent reference
 * References user by ID only
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "briefing_versions")
public class BriefingVersion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "briefing_id", nullable = false)
    private Briefing briefing;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "form_structure", columnDefinition = "JSONB")
    private String formStructure;

    @Column(name = "client_responses", columnDefinition = "JSONB")
    private String clientResponses;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
