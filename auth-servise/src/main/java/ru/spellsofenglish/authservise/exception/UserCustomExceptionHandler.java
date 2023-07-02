package ru.spellsofenglish.authservise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class UserCustomExceptionHandler {
    @ExceptionHandler(UserCustomException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserCustomException ex) {
        HttpStatus httpStatus = switch (ex.getLocation()) {
            case TOKEN_SERVICE_CONFLICT -> HttpStatus.CONFLICT;
            case USER_SERVICE_VALIDATION, TOKEN_SERVICE_VALIDATION, USER_DTO_ENTITY_VALIDATION ->
                    HttpStatus.BAD_REQUEST;
            case USER_SERVICE_NOT_FOUND, TOKEN_SERVICE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case TOKEN_SERVICE_FORBIDDEN -> HttpStatus.FORBIDDEN;
        };
        return ResponseEntity.status(httpStatus).body(createExceptionResponse(ex.getLocalizedMessage(), ex.getLocation()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse(
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(),
                ExceptionLocations.USER_DTO_ENTITY_VALIDATION));
    }

    private ExceptionResponse createExceptionResponse(String message, ExceptionLocations exceptionLocations) {
        return new ExceptionResponse(
                message,
                exceptionLocations,
                LocalDateTime.now()
        );
    }
}
