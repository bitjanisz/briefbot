package com.admeliora.briefbot.application.casestudy.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * CaseStudy Aggregate Root (DDD)
 * References account by ID only
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "case_studies")
@Filter(name = "accountFilter", condition = "account_id = :accountId")
public class CaseStudy extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "project_name", nullable = false, length = 255)
    private String projectName;

    @Column(name = "client_industry", length = 100)
    private String clientIndustry;

    @Column(name = "keywords", columnDefinition = "JSONB")
    private String keywords;

    @Column(name = "scope_summary", columnDefinition = "TEXT")
    private String scopeSummary;

    @Column(name = "challenges_solved", columnDefinition = "TEXT")
    private String challengesSolved;

    @Column(name = "budget_range_enum", length = 50)
    private String budgetRangeEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private CaseStudyStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "caseStudy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CaseStudyService> caseStudyServices = new HashSet<>();
}