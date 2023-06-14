package ru.spellsofenglish.player.exception;

public class InvalidDataException extends Exception{
    private final String title;
    public InvalidDataException(String message, String title) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
