package com.admeliora.briefbot.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Service for creating and validating JWT tokens
 */
@Slf4j
//@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long validityInMilliseconds;

    @Value("${security.jwt.issuer:briefbot}")
    private String issuer;

    @Value("${security.jwt.audience:briefbot-api}")
    private String audience;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration:86400000}") long validityInMilliseconds) {

        // Validate secret key length (minimum 256 bits / 32 bytes for HS256)
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException(
                "JWT secret key must be at least 256 bits (32 bytes) long. " +
                "Generate a secure key with: openssl rand -base64 64"
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
        log.info("JWT Token Provider initialized with HS256 algorithm, expiration: {} ms", validityInMilliseconds);
        log.debug("JWT secret key length: {} bytes", secret.getBytes(StandardCharsets.UTF_8).length);
    }

    /**
     * Create JWT token with user details and custom claims
     */
//    public String createToken(String email, Long userId, Long accountId, String givenName, String familyName, String authMethod) {
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds);

//        String token = Jwts.builder()
//                // Standard JWT Registered Claims (RFC 7519)
//                .issuer(issuer)                    // iss - who issued the token
//                .subject(email)                    // sub - who the token is about
//                .audience().add(audience).and()    // aud - who the token is for
//                .issuedAt(now)                     // iat - when was it issued
//                .expiration(validity)              // exp - when does it expire
//                // Custom application claims
//                .claim("userId", userId)
//                .claim("accountId", accountId)
//                .claim("email", email)
//                .claim("givenName", givenName)
//                .claim("familyName", familyName)
//                .claim("authMethod", authMethod)
//                // Sign with HS256
//                .signWith(secretKey)
//                .compact();

//        log.debug("Created JWT token for user: {}, userId: {}, accountId: {}, authMethod: {}",
//                email, userId, accountId, authMethod);
//        return token;
//    }

    /**
     * Create JWT token with user details (backward compatibility - uses email as both names)
     */
//    public String createToken(String email, Long userId, Long accountId) {
//        return createToken(email, userId, accountId, email, email, "unknown");
//    }

    /**
     * Validate token and extract claims
     * Verifies signature, expiration, issuer, and audience
     */
//    public Claims validateToken(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .verifyWith(secretKey)
//                    .requireIssuer(issuer)           // Verify issuer matches
//                    .requireAudience(audience)       // Verify audience matches
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//            log.debug("Token validated successfully for user: {}", claims.getSubject());
//            return claims;
//        } catch (Exception e) {
//            log.error("JWT validation failed: {}", e.getMessage());
//            throw new JwtAuthenticationException("Invalid JWT token", e);
//        }
//    }

    /**
     * Extract email from token
     */
//    public String getEmailFromToken(String token) {
//        return validateToken(token).getSubject();
//    }

    /**
     * Extract userId from token
     */
//    public Long getUserIdFromToken(String token) {
//        Claims claims = validateToken(token);
//        return claims.get("userId", Long.class);
//    }

    /**
     * Extract accountId from token
     */
//    public Long getAccountIdFromToken(String token) {
//        Claims claims = validateToken(token);
//        return claims.get("accountId", Long.class);
//    }

    /**
     * Extract given name (given name) from token
     */
//    public String getGivenNameFromToken(String token) {
//        Claims claims = validateToken(token);
//        return claims.get("givenName", String.class);
//    }

    /**
     * Extract family name (family name) from token
     */
//    public String getFamilyNameFromToken(String token) {
//        Claims claims = validateToken(token);
//        return claims.get("familyName", String.class);
//    }

    /**
     * Extract auth method from token
     */
//    public String getAuthMethodFromToken(String token) {
//        Claims claims = validateToken(token);
//        return claims.get("authMethod", String.class);
//    }

    /**
     * Check if token is expired
     */
//    public boolean isTokenExpired(String token) {
//        try {
//            Claims claims = validateToken(token);
//            return claims.getExpiration().before(new Date());
//        } catch (Exception e) {
//            return true;
//        }
//    }
}

