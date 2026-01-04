package com.admeliora.briefbot.adapter.in.web.account.mapper;

import com.admeliora.briefbot.adapter.in.web.account.model.response.AccountResponse;
import com.admeliora.briefbot.application.account.model.Account;

/**
 * AccountMapper (DDD-aligned)
 * Account no longer holds collections of child entities
 * Related entities must be queried separately via their repositories
 */
public class AccountMapper {
    public static AccountResponse toResponse(Account account) {
        if (account == null) return null;

        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getCreatedAt()
        );
    }
}


