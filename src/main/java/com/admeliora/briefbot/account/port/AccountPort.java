package com.admeliora.briefbot.account.port;

import com.admeliora.briefbot.domain.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountPort {
    List<Account> findAll();

    Account save(Account account);

    boolean existsById(Long id);

    Account getReferenceById(Long id);

    Optional<Account> findById(Long id);
}
