package com.admeliora.briefbot.adapter.in.web.account.dto;

import com.admeliora.briefbot.domain.account.Account;

public record AccountDto(
    Long accountId,
    String accountName
) {
    public static AccountDto from(Account account) {
        return new AccountDto(
            account.getId(),
            account.getName()
        );
    }
}
