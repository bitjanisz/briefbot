package com.admeliora.briefbot.infrastructure.adapter.out.persistence.account;

import com.admeliora.briefbot.account.port.UserAccountPort;
import com.admeliora.briefbot.account.model.UserAccount;
import com.admeliora.briefbot.account.model.UserAccountId;
import com.admeliora.briefbot.infrastructure.adapter.out.persistence.account.jpa.UserAccountRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAccountAdapter implements UserAccountPort {

    private final UserAccountRepositoryJpa userAccountRepository;

    @Override
    public Optional<UserAccount> findById(UserAccountId id) {
        return userAccountRepository.findById(id);
    }

    @Override
    public UserAccount save(UserAccount ua) {
        return userAccountRepository.save(ua);
    }

    @Override
    public void deleteById(UserAccountId id) {
        userAccountRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UserAccountId id) {
        return userAccountRepository.existsById(id);
    }

    @Override
    public List<UserAccount> findByUserId(Long userId) {
        return userAccountRepository.findByUser_Id(userId);
    }

    @Override
    public List<UserAccount> findByAccountId(Long accountId) {
        return userAccountRepository.findByAccount_Id(accountId);
    }
}
