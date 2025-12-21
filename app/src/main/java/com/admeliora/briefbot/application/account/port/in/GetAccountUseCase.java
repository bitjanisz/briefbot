package com.admeliora.briefbot.application.account.port.in;

import com.admeliora.briefbot.application.account.model.Account;

import java.util.Optional;

public interface GetAccountUseCase {
    Optional<Account> execute(Long id);
}

