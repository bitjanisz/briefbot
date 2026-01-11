package com.admeliora.briefbot.application.service.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Aggregate Root (DDD)
 * Manages ServiceRelation as part of its aggregate
 * References account by ID only
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "services")
@Filter(name = "accountFilter", condition = "account_id = :accountId")
public class Service extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "vat_rate", precision = 5, scale = 2)
    private BigDecimal vatRate;

    @Column(length = 3)
    private String currency;

    @Column(name = "pricing_unit", nullable = false, length = 50)
    private String pricingUnit;

    @Column(name = "min_price_threshold", precision = 12, scale = 2)
    private BigDecimal minPriceThreshold;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

//    @Builder.Default
//    @OneToMany(mappedBy = "parentService", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<ServiceRelation> serviceRelations = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}
