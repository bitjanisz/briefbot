package com.admeliora.briefbot.application.account.port.out;

import com.admeliora.briefbot.application.account.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountPort {
    Optional<UserAccount> findById(Long id);

    UserAccount save(UserAccount ua);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<UserAccount> findByUserId(Long userId);

    List<UserAccount> findByAccountId(Long accountId);

    Optional<UserAccount> findPrimaryAccountIdByUserEmail(String email);

    boolean existsByUserIdAndAccountId(Long userId, Long accountId);
}
