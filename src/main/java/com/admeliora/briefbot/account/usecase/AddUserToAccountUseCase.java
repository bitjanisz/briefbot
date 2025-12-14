package com.admeliora.briefbot.account.usecase;

import com.admeliora.briefbot.account.api.dto.AccountDto;
import com.admeliora.briefbot.domain.account.AccountRole;
import com.admeliora.briefbot.domain.account.UserAccount;
import com.admeliora.briefbot.domain.account.UserAccountId;
import com.admeliora.briefbot.account.port.AccountPort;
import com.admeliora.briefbot.account.port.UserAccountPort;
import com.admeliora.briefbot.account.port.in.AddUserToAccountInPort;
import com.admeliora.briefbot.user.port.UserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddUserToAccountUseCase implements AddUserToAccountInPort {

    private final AccountPort accountPort;
    private final UserPort userPort;
    private final UserAccountPort userAccountPort;

    @Override
    @Transactional
    public AccountDto execute(Long accountId, Long userId, AccountRole role) {
        AccountRole effectiveRole = role != null ? role : AccountRole.MEMBER;

        if (!userPort.existsById(userId)) {
            throw new EntityNotFoundException("User not found: " + userId);
        }
        if (!accountPort.existsById(accountId)) {
            throw new EntityNotFoundException("Account not found: " + accountId);
        }

        UserAccountId id = new UserAccountId(userId, accountId);

        userAccountPort.findById(id)
                .map(existing -> {
                    existing.setRole(effectiveRole);
                    return userAccountPort.save(existing);
                })
                .orElseGet(() -> {
                    UserAccount link = new UserAccount();
                    link.setId(id);
                    link.setUser(userPort.getReferenceById(userId));
                    link.setAccount(accountPort.getReferenceById(accountId));
                    link.setRole(effectiveRole);
                    return userAccountPort.save(link);
                });
        return accountPort.findById(accountId)
                .map(AccountDto::from)
                .orElseThrow();
    }
}
