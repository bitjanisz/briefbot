package com.admeliora.briefbot.adapter.out.password;

import com.admeliora.briefbot.application.user.port.out.PasswordGeneratorPort;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Default password generator - generates random secure passwords
 * Active for all profiles except 'test'
 */
@Component
public class RandomPasswordGenerator implements PasswordGeneratorPort {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateTemporaryPassword() {
        byte[] randomBytes = new byte[12];
        RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

