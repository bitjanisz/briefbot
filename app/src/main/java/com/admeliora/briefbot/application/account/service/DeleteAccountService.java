package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.application.account.port.in.DeleteAccountUseCase;
import com.admeliora.briefbot.application.account.port.out.AccountPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAccountService implements DeleteAccountUseCase {

    private final AccountPort accountPort;

    @Override
    @Transactional
    public void execute(Long id) {
        Account account = accountPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
        accountPort.delete(account);
    }
}

