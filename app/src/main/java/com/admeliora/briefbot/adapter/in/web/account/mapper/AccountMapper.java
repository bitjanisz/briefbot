package com.admeliora.briefbot.adapter.in.web.account.mapper;

import com.admeliora.briefbot.adapter.in.web.account.model.response.AccountResponse;
import com.admeliora.briefbot.adapter.in.web.account.model.response.AccountUserResponse;
import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.model.UserAccountDetails;

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

    public static AccountUserResponse toUserResponse(UserAccountDetails userAccountDetails) {
        if (userAccountDetails == null) return null;

        return new AccountUserResponse(
                userAccountDetails.id(),
                userAccountDetails.userId(),
                userAccountDetails.userEmail(),
                userAccountDetails.userGivenName(),
                userAccountDetails.userFamilyName(),
                userAccountDetails.role(),
                userAccountDetails.createdAt()
        );
    }
}
