package com.ifce.jedi.exception.custom;

import java.io.IOException;

public class UploadException extends RuntimeException {
    public UploadException(String message, IOException e) {
        super(message);
    }
}
