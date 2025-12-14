package com.admeliora.briefbot.infrastructure.adapter.in.web.account;

import com.admeliora.briefbot.account.port.in.AddUserToAccountPort;
import com.admeliora.briefbot.account.port.in.CreateAccountPort;
import com.admeliora.briefbot.account.port.in.ListAccountsPort;
import com.admeliora.briefbot.account.port.in.RemoveUserFromAccountPort;
import com.admeliora.briefbot.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.account.port.in.command.CreateAccountCommand;
import com.admeliora.briefbot.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.infrastructure.adapter.in.web.account.mapper.AccountMapper;
import com.admeliora.briefbot.infrastructure.adapter.in.web.account.model.request.AddUserToAccountRequest;
import com.admeliora.briefbot.infrastructure.adapter.in.web.account.model.request.CreateAccountRequest;
import com.admeliora.briefbot.infrastructure.adapter.in.web.account.model.response.AccountResponse;
import com.admeliora.briefbot.infrastructure.adapter.in.web.account.model.response.AccountSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Accounts", description = "Accounts API (adapters)")
public class AccountInRestAdapter {

    private final ListAccountsPort listAccountsPort;
    private final CreateAccountPort createAccountPort;
    private final AddUserToAccountPort addUserToAccountPort;
    private final RemoveUserFromAccountPort removeUserFromAccountPort;

    @GetMapping
    @Operation(summary = "List accounts")
    public List<AccountSummaryResponse> listAccounts(@AuthenticationPrincipal OidcUser oidcUser) {
        return listAccountsPort.execute().stream()
                .map(AccountMapper::toSummary)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account")
    public AccountResponse createAccount(@AuthenticationPrincipal OidcUser oidcUser,
                                 @Valid @RequestBody CreateAccountRequest request) {
        var command = new CreateAccountCommand(request.name(), request.ownerId());
        var account = createAccountPort.execute(command);
        return AccountMapper.toResponse(account);
    }

    @PutMapping("/{accountId}/users")
    @Operation(summary = "Add or update user on account")
    public AccountResponse addUserToAccount(@PathVariable Long accountId,
                                       @Valid @RequestBody AddUserToAccountRequest request) {
        var command = new AddUserToAccountCommand(accountId, request.userId(), request.role());
        var account = addUserToAccountPort.execute(command);
        return AccountMapper.toResponse(account);
    }

    @DeleteMapping("/{accountId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove user from account")
    public AccountResponse removeUserFromAccount(@PathVariable Long accountId, @PathVariable Long userId) {
        var command = new RemoveUserFromAccountCommand(accountId, userId);
        var account = removeUserFromAccountPort.execute(command);
        return AccountMapper.toResponse(account);
    }
}
