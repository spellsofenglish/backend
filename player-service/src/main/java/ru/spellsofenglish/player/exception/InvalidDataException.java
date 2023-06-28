package ru.spellsofenglish.player.exception;

//рантайм эксепшн
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
