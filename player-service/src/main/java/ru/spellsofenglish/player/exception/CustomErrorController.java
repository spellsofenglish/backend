package ru.spellsofenglish.player.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomErrorController  {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> validationException =  new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationException.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return validationException;
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(InvalidDataException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTitle(exception.getTitle());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDateTimeError(ZonedDateTime.now());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
