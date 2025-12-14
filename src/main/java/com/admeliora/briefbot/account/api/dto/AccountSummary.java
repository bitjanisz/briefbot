package com.admeliora.briefbot.account.api.dto;

public record AccountSummary(
        Long accountId,
        String accountName
) {

    public static AccountSummary from(com.admeliora.briefbot.account.domain.Account account) {
        return new AccountSummary(
                account.getId(),
                account.getName()
        );
    }
}