package com.admeliora.briefbot.adapter.out.persistence.account;

import com.admeliora.briefbot.application.account.port.out.AccountPort;
import com.admeliora.briefbot.application.account.model.Account;
import com.admeliora.briefbot.adapter.out.persistence.account.jpa.AccountRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountAdapter implements AccountPort {

    private final AccountRepositoryJpa accountRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    @Override
    public Account getReferenceById(Long id) {
        return accountRepository.getReferenceById(id);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }
}
