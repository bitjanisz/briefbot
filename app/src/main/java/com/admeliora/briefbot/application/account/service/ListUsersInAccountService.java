package com.admeliora.briefbot.application.account.service;

import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.model.UserAccountDetails;
import com.admeliora.briefbot.application.account.port.in.ListUsersInAccountUseCase;
import com.admeliora.briefbot.application.account.port.out.UserAccountPort;
import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListUsersInAccountService implements ListUsersInAccountUseCase {

    private final UserAccountPort userAccountPort;
    private final UserPort userPort;

    @Override
    public List<UserAccountDetails> execute(Long accountId) {
        List<UserAccount> userAccounts = userAccountPort.findByAccountId(accountId);
        return userAccounts.stream()
                .map(ua -> {
                    User user = userPort.findById(ua.getUserId()).orElse(null);
                    return new UserAccountDetails(
                            ua.getId(),
                            ua.getUserId(),
                            user != null ? user.getEmail() : null,
                            user != null ? user.getGivenName() : null,
                            user != null ? user.getFamilyName() : null,
                            ua.getRole(),
                            ua.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }
}
