package com.admeliora.briefbot.application.briefing.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Briefing Aggregate Root (DDD)
 * Manages BriefingVersion as part of its aggregate
 * References account and client by ID only
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "briefings")
@Filter(name = "accountFilter", condition = "account_id = :accountId")
public class Briefing extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(length = 50)
    private String status;

    @Builder.Default
    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("versionNumber DESC")
    private List<BriefingVersion> versions = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
