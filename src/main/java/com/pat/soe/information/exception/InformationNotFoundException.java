package com.pat.soe.information.exception;

public class InformationNotFoundException extends InformationException {
    public InformationNotFoundException() {
    }

    public InformationNotFoundException(String message) {
        super(message);
    }

    public InformationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformationNotFoundException(Throwable cause) {
        super(cause);
    }
}
