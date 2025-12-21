package com.admeliora.briefbot.application.user.port.out;

/**
 * Port for sending emails
 */
public interface EmailPort {

    /**
     * Send temporary password to user email
     *
     * @param to recipient email
     * @param givenName recipient first name
     * @param temporaryPassword generated password
     */
    void sendTemporaryPassword(String to, String givenName, String temporaryPassword);
}

