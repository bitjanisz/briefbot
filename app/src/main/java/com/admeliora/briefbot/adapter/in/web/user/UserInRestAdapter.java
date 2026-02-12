package com.admeliora.briefbot.adapter.in.web.user;

import com.admeliora.briefbot.adapter.in.web.user.mapper.UserMapper;
import com.admeliora.briefbot.adapter.in.web.user.model.request.UserUpdateRequest;
import com.admeliora.briefbot.adapter.in.web.user.model.response.UserResponse;
import com.admeliora.briefbot.application.user.port.in.GetLoggedUserUseCase;
import com.admeliora.briefbot.application.user.port.in.ListUsersUseCase;
import com.admeliora.briefbot.application.user.port.in.UpdateOwnUserUseCase;
import com.admeliora.briefbot.application.user.port.in.command.UpdateOwnUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management API")
public class UserInRestAdapter {

    private final ListUsersUseCase listUsersUseCase;
    private final GetLoggedUserUseCase getLoggedUserUseCase;
    private final UpdateOwnUserUseCase updateOwnUserUseCase;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users.",
            operationId = "getAllUsers"
    )
    public List<UserResponse> getAllUsers() {
        return listUsersUseCase.execute().stream().map(UserMapper::toResponse).toList();
    }

    @GetMapping("me")
    @Operation(
            summary = "Get logged-in user",
            description = "Retrieves information about the currently authenticated user (OAuth2 or form-based).",
            operationId = "getLoggedInUser"
    )
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        return getLoggedUserUseCase.execute(authentication)
                .map(UserMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("me")
    @Operation(
            summary = "Update logged-in user",
            description = "Updates information for the currently authenticated user (OAuth2 or form-based).",
            operationId = "updateLoggedInUser"
    )
    public ResponseEntity<UserResponse> updateMe(
            Authentication authentication,
            @RequestBody @Validated UserUpdateRequest updateRequest) {

        // Get user email from authentication
        String email = getEmailFromAuthentication(authentication);
        if (email == null) {
            return ResponseEntity.badRequest().build();
        }

        var command = new UpdateOwnUserCommand(
                email,
                updateRequest.givenName(),
                updateRequest.familyName(),
                updateRequest.picture()
        );

        return updateOwnUserUseCase.execute(command)
                .map(UserMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Extract email from Authentication object
     * Supports both OAuth2 (OidcUser) and form-based (UserDetails) authentication
     */
    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        // OAuth2 login
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            return oidcUser.getAttribute("email");
        }

        // Form-based login - username is email
        return authentication.getName();
    }
}
