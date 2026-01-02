package com.admeliora.briefbot.adapter.out.persistence.account;

import com.admeliora.briefbot.adapter.out.persistence.account.jpa.UserAccountRepositoryJpa;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAccountAdapter implements UserAccountPort {

    private final UserAccountRepositoryJpa userAccountRepository;

    @Override
    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public UserAccount save(UserAccount ua) {
        return userAccountRepository.save(ua);
    }

    @Override
    public void deleteById(Long id) {
        userAccountRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userAccountRepository.existsById(id);
    }

    @Override
    public List<UserAccount> findByUserId(Long userId) {
        return userAccountRepository.findByUserId(userId);
    }

    @Override
    public List<UserAccount> findByAccountId(Long accountId) {
        return userAccountRepository.findByAccountId(accountId);
    }

    @Override
    public Optional<UserAccount> findPrimaryAccountIdByUserEmail(String email) {
        return userAccountRepository.findByUserEmail(email).stream().findFirst();
    }

    @Override
    public boolean existsByUserIdAndAccountId(Long userId, Long accountId) {
        return userAccountRepository.existsByUserIdAndAccountId(userId, accountId);
    }
}
