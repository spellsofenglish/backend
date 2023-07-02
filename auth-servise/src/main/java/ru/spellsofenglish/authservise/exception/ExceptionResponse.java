package ru.spellsofenglish.authservise.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(String message,
                                ExceptionLocations typeException,
                                LocalDateTime exceptionDateTime) {
}
