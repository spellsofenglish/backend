package com.pat.soe.service.exception;

public class SoeException extends RuntimeException {
    public SoeException() {
    }

    public SoeException(String message) {
        super(message);
    }

    public SoeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoeException(Throwable cause) {
        super(cause);
    }
}
