package ru.spellsofenglish.player.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorResponse {
    private String title;
    private String message;
    private ZonedDateTime dateTimeError;
}
