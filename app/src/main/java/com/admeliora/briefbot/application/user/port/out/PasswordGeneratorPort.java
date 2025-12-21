package com.admeliora.briefbot.application.user.port.out;

/**
 * Port for generating passwords
 */
public interface PasswordGeneratorPort {

    /**
     * Generate a temporary password for new users
     *
     * @return generated password
     */
    String generateTemporaryPassword();
}

