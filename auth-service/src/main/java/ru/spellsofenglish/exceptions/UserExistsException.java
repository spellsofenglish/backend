package ru.spellsofenglish.exceptions;

public class UserExistsException extends RuntimeException {
    private final String title;

    public UserExistsException(String message, String title) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
