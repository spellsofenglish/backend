package com.pat.soe.exception;

public class UserCustomException extends RuntimeException {
    private final ExceptionLocations location;
    public UserCustomException(String message, ExceptionLocations location) {
        super(message);
        this.location = location;
    }

    public ExceptionLocations getLocation() {
        return location;
    }
}
