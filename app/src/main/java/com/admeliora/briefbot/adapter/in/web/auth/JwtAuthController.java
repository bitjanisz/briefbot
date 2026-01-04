package com.admeliora.briefbot.adapter.in.web.auth;

import com.admeliora.briefbot.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST API for JWT token validation
 * Note: Token generation is handled automatically by authentication success handlers
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "JWT token validation")
public class JwtAuthController {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Validate JWT token
     */
    @GetMapping("/validate")
    @Operation(summary = "Validate JWT token", description = "Check if JWT token is valid and extract claims")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtTokenProvider.getEmailFromToken(token);
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            Long accountId = jwtTokenProvider.getAccountIdFromToken(token);
            String givenName = jwtTokenProvider.getGivenNameFromToken(token);
            String familyName = jwtTokenProvider.getFamilyNameFromToken(token);
            String authMethod = jwtTokenProvider.getAuthMethodFromToken(token);

            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("email", email);
            response.put("userId", userId);
            response.put("accountId", accountId);
            response.put("givenName", givenName);
            response.put("familyName", familyName);
            response.put("authMethod", authMethod);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(Map.of("valid", false, "error", "Invalid token"));
        }
    }
}

