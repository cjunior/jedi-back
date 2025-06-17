package com.ifce.jedi.exception.custom;

public class EmailSendingException extends RuntimeException     {
    public EmailSendingException(String message, Exception e) {
        super(message);
    }
}
