package com.admeliora.briefbot.application.companyprofile.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * CompanyProfile Aggregate Root (DDD)
 * References account by ID only
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "company_profiles")
@Filter(name = "accountFilter", condition = "account_id = :accountId")
public class CompanyProfile extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "company_legal_name", length = 255)
    private String companyLegalName;

    @Column(name = "tax_id", length = 50)
    private String taxId;

    @Column(name = "address_line", columnDefinition = "TEXT")
    private String addressLine;

    @Column(name = "contact_email", length = 150)
    private String contactEmail;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "short_value_proposition", columnDefinition = "TEXT")
    private String shortValueProposition;

    @Column(name = "core_values", columnDefinition = "JSONB")
    private String coreValues;

    @Column(name = "ai_tone_style", length = 100)
    private String aiToneStyle;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
