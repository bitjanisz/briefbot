package com.admeliora.briefbot.application.offer.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OfferVersion - Child entity of Offer aggregate (DDD)
 * Part of Offer aggregate, maintains parent reference
 * Manages OfferVersionItem as its own children
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "offer_versions")
public class OfferVersion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "introduction_content", columnDefinition = "TEXT")
    private String introductionContent;

    @Column(name = "scope_content", columnDefinition = "TEXT")
    private String scopeContent;

    @Column(name = "methodology_content", columnDefinition = "TEXT")
    private String methodologyContent;

    @Column(name = "summary_content", columnDefinition = "TEXT")
    private String summaryContent;

    @Column(name = "total_netto", precision = 12, scale = 2)
    private BigDecimal totalNetto;

    @Column(name = "total_brutto", precision = 12, scale = 2)
    private BigDecimal totalBrutto;

    @Column(length = 3)
    private String currency;

    @Column(name = "has_spelling_errors", nullable = false)
    private Boolean hasSpellingErrors;

    @Column(name = "ai_suggestions", columnDefinition = "JSONB")
    private String aiSuggestions;

    @Builder.Default
    @OneToMany(mappedBy = "offerVersion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OfferVersionItem> items = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
