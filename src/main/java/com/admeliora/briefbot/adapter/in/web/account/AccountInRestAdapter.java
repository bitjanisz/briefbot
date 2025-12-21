package com.admeliora.briefbot.adapter.in.web.account;

import com.admeliora.briefbot.application.account.port.in.AddUserToAccountUseCase;
import com.admeliora.briefbot.application.account.port.in.CreateAccountUseCase;
import com.admeliora.briefbot.application.account.port.in.ListAccountsUseCase;
import com.admeliora.briefbot.application.account.port.in.RemoveUserFromAccountUseCase;
import com.admeliora.briefbot.application.account.port.in.command.AddUserToAccountCommand;
import com.admeliora.briefbot.application.account.port.in.command.CreateAccountCommand;
import com.admeliora.briefbot.application.account.port.in.command.RemoveUserFromAccountCommand;
import com.admeliora.briefbot.adapter.in.web.account.mapper.AccountMapper;
import com.admeliora.briefbot.adapter.in.web.account.model.request.AddUserToAccountRequest;
import com.admeliora.briefbot.adapter.in.web.account.model.request.CreateAccountRequest;
import com.admeliora.briefbot.adapter.in.web.account.model.response.AccountResponse;
import com.admeliora.briefbot.adapter.in.web.account.model.response.AccountSummaryResponse;
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

    private final ListAccountsUseCase listAccountsUseCase;
    private final CreateAccountUseCase createAccountPort;
    private final AddUserToAccountUseCase addUserToAccountUseCase;
    private final RemoveUserFromAccountUseCase removeUserFromAccountUseCase;

    @GetMapping
    @Operation(summary = "List accounts")
    public List<AccountSummaryResponse> listAccounts(@AuthenticationPrincipal OidcUser oidcUser) {
        return listAccountsUseCase.execute().stream()
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
        var account = addUserToAccountUseCase.execute(command);
        return AccountMapper.toResponse(account);
    }

    @DeleteMapping("/{accountId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove user from account")
    public AccountResponse removeUserFromAccount(@PathVariable Long accountId, @PathVariable Long userId) {
        var command = new RemoveUserFromAccountCommand(accountId, userId);
        var account = removeUserFromAccountUseCase.execute(command);
        return AccountMapper.toResponse(account);
    }
}
