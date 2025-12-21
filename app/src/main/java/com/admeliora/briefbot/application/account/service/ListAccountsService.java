package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.adapter.out.persistence.account.jpa.AccountRepositoryJpa;
import com.admeliora.briefbot.application.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAccountsService implements com.admeliora.briefbot.application.account.port.in.ListAccountsUseCase {

    private final AccountRepositoryJpa accountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Account> execute() {
        return accountRepository.findAll();
    }
}
