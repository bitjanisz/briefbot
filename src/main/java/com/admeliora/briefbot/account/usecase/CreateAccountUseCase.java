package com.admeliora.briefbot.account.usecase;

import com.admeliora.briefbot.account.api.dto.AccountDto;
import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.domain.account.AccountRole;
import com.admeliora.briefbot.domain.account.UserAccount;
import com.admeliora.briefbot.domain.account.UserAccountId;
import com.admeliora.briefbot.account.port.AccountPort;
import com.admeliora.briefbot.account.port.UserAccountPort;
import com.admeliora.briefbot.account.port.in.CreateAccountInPort;
import com.admeliora.briefbot.user.port.UserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase implements CreateAccountInPort {

    private final AccountPort accountPort;
    private final UserPort userPort;
    private final UserAccountPort userAccountPort;

    @Override
    @Transactional
    public AccountDto execute(String accountName, Long userId) {
        if (!userPort.existsById(userId)) {
            throw new EntityNotFoundException("User not found: " + userId);
        }

        Account account = new Account();
        account.setName(requireText(accountName));
        account = accountPort.save(account);

        UserAccount link = new UserAccount();
        link.setId(new UserAccountId(userId, account.getId()));
        link.setUser(userPort.getReferenceById(userId));
        link.setAccount(account);
        link.setRole(AccountRole.OWNER);

        userAccountPort.save(link);
        return AccountDto.from(account);
    }

    private static String requireText(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("accountName must not be blank");
        }
        return value;
    }
}
