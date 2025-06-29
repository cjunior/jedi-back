package com.ifce.jedi.exception.custom;

public class RegistrationAlreadyMadeException extends RuntimeException {
    public RegistrationAlreadyMadeException(String message) {
        super(message);
    }
}
