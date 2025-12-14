package com.admeliora.briefbot.account.usecase;

import com.admeliora.briefbot.account.port.AccountPort;
import com.admeliora.briefbot.account.port.UserAccountPort;
import com.admeliora.briefbot.account.port.in.CreateAccountInPort;
import com.admeliora.briefbot.account.port.in.command.CreateAccountCommand;
import com.admeliora.briefbot.domain.account.Account;
import com.admeliora.briefbot.domain.account.AccountRole;
import com.admeliora.briefbot.domain.account.UserAccount;
import com.admeliora.briefbot.domain.account.UserAccountId;
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
    public Account execute(CreateAccountCommand command) {
        Long ownerId = command.ownerId();
        String name = command.name();

        if (!userPort.existsById(ownerId)) {
            throw new EntityNotFoundException("User not found: " + ownerId);
        }

        Account account = new Account();
        account.setName(requireText(name));
        account = accountPort.save(account);

        UserAccount link = new UserAccount();
        link.setId(new UserAccountId(ownerId, account.getId()));
        link.setUser(userPort.getReferenceById(ownerId));
        link.setAccount(account);
        link.setRole(AccountRole.OWNER);

        userAccountPort.save(link);
        return account;
    }

    private static String requireText(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("accountName must not be blank");
        }
        return value;
    }
}
