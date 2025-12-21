package com.admeliora.briefbot.application.account.port.out;

import com.admeliora.briefbot.application.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountPort {
    List<Account> findAll();

    Account save(Account account);

    boolean existsById(Long id);

    Account getReferenceById(Long id);

    Optional<Account> findById(Long id);

    void delete(Account account);
}
