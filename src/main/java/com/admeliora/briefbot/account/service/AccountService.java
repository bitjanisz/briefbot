package com.admeliora.briefbot.account.service;

import com.admeliora.briefbot.account.api.dto.AccountSummary;
import com.admeliora.briefbot.account.domain.Account;
import com.admeliora.briefbot.account.domain.AccountRole;
import com.admeliora.briefbot.account.domain.UserAccount;
import com.admeliora.briefbot.account.domain.UserAccountId;
import com.admeliora.briefbot.account.repository.AccountRepository;
import com.admeliora.briefbot.account.repository.UserAccountRepository;
import com.admeliora.briefbot.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<AccountSummary> listAccounts() {

//        if (!userRepository.existsById(userId)) {
//            throw new EntityNotFoundException("User not found: " + userId);
//        }

        return accountRepository.findAll().stream()
                .map(AccountSummary::from)
                .toList();
    }

    @Transactional
    public Account createAccountForUser(String accountName, Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found: " + userId);
        }

        Account account = new Account();
        account.setName(requireText(accountName, "accountName"));
        account = accountRepository.save(account);

        UserAccount link = new UserAccount();
        link.setId(new UserAccountId(userId, account.getId()));
        link.setUser(userRepository.getReferenceById(userId));
        link.setAccount(account);
        link.setRole(AccountRole.OWNER);

        userAccountRepository.save(link);
        return account;
    }

    @Transactional
    public UserAccount addUserToAccount(Long accountId, Long userId, AccountRole role) {
        AccountRole effectiveRole = role != null ? role : AccountRole.MEMBER;

        UserAccountId id = new UserAccountId(userId, accountId);

        return userAccountRepository.findById(id)
                .map(existing -> {
                    existing.setRole(effectiveRole);
                    return existing;
                })
                .orElseGet(() -> {
                    UserAccount link = new UserAccount();
                    link.setId(id);
                    link.setUser(userRepository.getReferenceById(userId));
                    link.setAccount(accountRepository.getReferenceById(accountId));
                    link.setRole(effectiveRole);
                    return userAccountRepository.save(link);
                });
    }

    /**
     * Removes user-account link.
     */
    @Transactional
    public void removeUserFromAccount(Long accountId, Long userId) {
        UserAccountId id = new UserAccountId(userId, accountId);

        // Efficient delete without loading entity (still checks count)
        if (!userAccountRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "UserAccount not found for userId=" + userId + ", accountId=" + accountId
            );
        }
        userAccountRepository.deleteById(id);
    }

    private static String normalizeRole(String role) {
        String r = requireText(role, "role").trim().toUpperCase();
        // optionally validate allowed roles:
        // if (!Set.of("OWNER","ADMIN","MEMBER","VIEWER").contains(r)) throw ...
        return r;
    }

    private static String requireText(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}