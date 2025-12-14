package com.admeliora.briefbot.account.usecase;

import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.account.port.in.ListAccountsInPort;
import com.admeliora.briefbot.adapter.out.persistence.account.AccountRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAccountsUseCase implements ListAccountsInPort {

    private final AccountRepositoryJpa accountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Account> execute() {
        return accountRepository.findAll();
    }
}
