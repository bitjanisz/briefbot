package com.admeliora.briefbot.adapter.in.web.auth;

import com.admeliora.briefbot.adapter.in.web.user.model.request.LoginRequest;
import com.admeliora.briefbot.adapter.in.web.user.model.request.RegisterUserRequest;
import com.admeliora.briefbot.adapter.in.web.user.model.response.AuthResponse;
import com.admeliora.briefbot.adapter.out.persistence.account.jpa.UserAccountRepositoryJpa;
import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.user.model.User;
import com.admeliora.briefbot.application.user.port.in.LoginPort;
import com.admeliora.briefbot.application.user.port.in.RegisterUserPort;
import com.admeliora.briefbot.application.user.port.in.command.LoginCommand;
import com.admeliora.briefbot.application.user.port.in.command.RegisterUserCommand;
import com.admeliora.briefbot.security.jwt.JwtProperties;
import com.admeliora.briefbot.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication operations (registration and login)
 */
@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and login endpoints")
public class AuthRestController {

    private final RegisterUserPort registerUserPort;
    private final LoginPort loginPort;

    private final UserAccountRepositoryJpa userAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final JwtProperties jwtProperties;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user with email. A temporary password will be sent to the provided email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        log.info("Registering user: {}", request.email());

        RegisterUserCommand command = RegisterUserCommand.builder()
                .email(request.email())
                .givenName(request.givenName())
                .familyName(request.familyName())
                .build();

        User user = registerUserPort.register(command);

        AuthResponse response = AuthResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .message("Registration successful. A temporary password has been sent to your email.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping({"/login", "/logon"})
    @Operation(summary = "Login with email and password (also available under /logon)", description = "Authenticate user using email and password credentials; on success an HttpOnly cookie named BRIEFBOT_JWT with a signed JWT is set in the response.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful (sets HttpOnly cookie 'BRIEFBOT_JWT')",
                    headers = @Header(name = "Set-Cookie", description = "HttpOnly cookie 'BRIEFBOT_JWT' containing the JWT token", schema = @Schema(type = "string")),
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        log.info("Login attempt for user: {}", request.email());

        LoginCommand command = LoginCommand.builder()
                .email(request.email())
                .password(request.password())
                .build();

        User user = loginPort.login(command);

        // Get user's account
        Long accountId = userAccountRepository.findByUserEmail(user.getEmail())
                .stream()
                .findFirst()
                .map(UserAccount::getAccountId)
                .orElse(null);

        // Generate JWT token with all user details
        String jwtToken = jwtTokenProvider.createToken(
                user.getEmail(),
                user.getId(),
                accountId,
                user.getGivenName(),
                user.getFamilyName(),
                "form"
        );

        var jwtCookieConfig = jwtProperties.getCookie();

        // Store JWT token in HTTP-only cookie
        Cookie jwtCookie = new Cookie(jwtCookieConfig.getName(), jwtToken);
        jwtCookie.setHttpOnly(jwtCookieConfig.isHttpOnly());
        jwtCookie.setSecure(jwtCookieConfig.isSecure());
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(jwtCookieConfig.getMaxAge());
        jwtCookie.setAttribute("SameSite", jwtCookieConfig.getSameSite());
        response.addCookie(jwtCookie);

        AuthResponse authResponse = AuthResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .message("Login successful")
                .build();

        return ResponseEntity.ok(authResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Authentication error: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
