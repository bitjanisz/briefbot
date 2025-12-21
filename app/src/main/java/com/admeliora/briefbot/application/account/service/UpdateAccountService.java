package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.UpdateAccountUseCase;
import com.admeliora.briefbot.application.account.port.in.command.UpdateAccountCommand;
import com.admeliora.briefbot.application.account.port.out.AccountPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAccountService implements UpdateAccountUseCase {

    private final AccountPort accountPort;

    @Override
    @Transactional
    public Account execute(UpdateAccountCommand command) {
        Account account = accountPort.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + command.id()));

        account.setName(command.name());

        return accountPort.save(account);
    }
}

