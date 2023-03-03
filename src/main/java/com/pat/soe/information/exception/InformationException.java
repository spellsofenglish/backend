package com.pat.soe.information.exception;

public class InformationException extends RuntimeException {
    public InformationException() {
    }

    public InformationException(String message) {
        super(message);
    }

    public InformationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformationException(Throwable cause) {
        super(cause);
    }
}
