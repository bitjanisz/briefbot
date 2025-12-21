package com.admeliora.briefbot.application.common.exception;

/**
 * Exception thrown when user tries to perform operation but is not associated with any account
 */
public class NoAccountAssignedException extends RuntimeException {

    public NoAccountAssignedException() {
        super("User is not associated with any account");
    }

    public NoAccountAssignedException(String userEmail) {
        super(String.format("User %s is not associated with any account", userEmail));
    }

    public NoAccountAssignedException(String email, String operation) {
        super(String.format("User %s is not associated with any account and cannot perform: %s", email, operation));
    }
}

