package com.admeliora.briefbot.account.api;

import com.admeliora.briefbot.account.api.dto.AccountSummary;
import com.admeliora.briefbot.account.api.dto.AddUserToAccountDto;
import com.admeliora.briefbot.account.api.dto.CreateAccountDto;
import com.admeliora.briefbot.account.domain.Account;
import com.admeliora.briefbot.account.domain.UserAccount;
import com.admeliora.briefbot.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountSummary> myAccounts(
            @AuthenticationPrincipal OidcUser oidcUser
    ) {
//        Long userId = userService
//                .getByOidcSub(oidcUser.getSubject())
//                .getId();

//        return accountService.listAccountsForUser(userId);
        return accountService.listAccounts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(
            @AuthenticationPrincipal OidcUser oidcUser,
            @Valid @RequestBody CreateAccountDto request
    ) {
        return accountService.createAccountForUser(
                request.name(),
                request.ownerId()
        );
    }

    @PutMapping("/{accountId}/users")
    public UserAccount addUserToAccount(
            @PathVariable Long accountId,
            @Valid @RequestBody AddUserToAccountDto request
    ) {
        return accountService.addUserToAccount(
                accountId,
                request.userId(),
                request.role()
        );
    }

    /**
     * Removes a user from an account.
     */
    @DeleteMapping("/{accountId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserFromAccount(
            @PathVariable Long accountId,
            @PathVariable Long userId
    ) {
        accountService.removeUserFromAccount(accountId, userId);
    }
}
