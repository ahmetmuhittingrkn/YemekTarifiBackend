package com.example.yemek_tarifi.exception;

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
} 