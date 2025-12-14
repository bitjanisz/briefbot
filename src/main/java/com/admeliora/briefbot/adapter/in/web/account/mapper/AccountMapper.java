package com.admeliora.briefbot.adapter.in.web.account.mapper;

import com.admeliora.briefbot.adapter.in.web.account.response.AccountResponse;
import com.admeliora.briefbot.adapter.in.web.account.response.AccountSummaryResponse;
import com.admeliora.briefbot.domain.account.Account;

public class AccountMapper {
    public static AccountResponse toResponse(Account account) {
        if (account == null) return null;
        return new AccountResponse(account.getId(), account.getName());
    }

    public static AccountSummaryResponse toSummary(Account account) {
        if (account == null) return null;
        return new AccountSummaryResponse(account.getId(), account.getName());
    }
}

