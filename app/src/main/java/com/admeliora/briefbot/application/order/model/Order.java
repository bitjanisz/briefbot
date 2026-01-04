package com.admeliora.briefbot.application.order.model;

import com.admeliora.briefbot.application.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

/**
 * Order Aggregate Root (DDD)
 * References account and offerVersion by ID only
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
@Filter(name = "accountFilter", condition = "account_id = :accountId")
public class Order extends BaseEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "offer_version_id", nullable = false, unique = true)
    private Long offerVersionId;

    @Column(name = "contract_status", nullable = false, length = 50)
    private String contractStatus;

    @Column(name = "contract_file_url", length = 255)
    private String contractFileUrl;

    @Column(name = "signed_at")
    private LocalDateTime signedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
