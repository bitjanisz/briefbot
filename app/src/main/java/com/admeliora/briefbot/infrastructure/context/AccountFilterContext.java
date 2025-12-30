package com.admeliora.briefbot.infrastructure.context;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
public class AccountFilterContext {

    private final EntityManager entityManager;
    @Getter
    private Long accountId;
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private String userEmail;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
        enableFilter();
    }

    private void enableFilter() {
        if (accountId != null) {
            Session session = entityManager.unwrap(Session.class);

            // Enable a single generic filter for all entities
            session.enableFilter("accountFilter")
                   .setParameter("accountId", accountId);

            log.debug("Enabled account filter with accountId: {}", accountId);
        }
    }

    public void disableFilter() {
        if (accountId != null) {
            Session session = entityManager.unwrap(Session.class);
            session.disableFilter("accountFilter");
            log.debug("Disabled account filter for accountId: {}", accountId);
        }
    }
}

