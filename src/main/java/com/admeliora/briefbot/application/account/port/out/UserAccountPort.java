package com.admeliora.briefbot.application.account.port.out;

import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.model.UserAccountId;

import java.util.List;
import java.util.Optional;

public interface UserAccountPort {
    Optional<UserAccount> findById(UserAccountId id);

    UserAccount save(UserAccount ua);

    void deleteById(UserAccountId id);

    boolean existsById(UserAccountId id);

    List<UserAccount> findByUserId(Long userId);

    List<UserAccount> findByAccountId(Long accountId);
}
