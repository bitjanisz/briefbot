package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.GetAccountUseCase;
import com.admeliora.briefbot.application.account.port.out.AccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAccountService implements GetAccountUseCase {

    private final AccountPort accountPort;

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> execute(Long id) {
        return accountPort.findById(id);
    }
}

