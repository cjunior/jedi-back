package com.ifce.jedi.exception.custom;

public class PreInscricaoDeletionException extends RuntimeException {
    public PreInscricaoDeletionException(String message) {
        super(message);
    }

    public PreInscricaoDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
