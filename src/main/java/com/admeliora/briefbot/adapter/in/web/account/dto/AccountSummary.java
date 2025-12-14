package com.admeliora.briefbot.adapter.in.web.account.dto;

import com.admeliora.briefbot.domain.account.Account;

public record AccountSummary(
        Long accountId,
        String accountName
) {
    public static AccountSummary from(Account account) {
        return new AccountSummary(account.getId(), account.getName());
    }
}
