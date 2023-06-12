package ru.spellsofenglish.player.exception;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler({MethodArgumentNotValidException.class, UnexpectedTypeException.class})
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(getErrorsMap(
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ProblemDetail handleNotFoundExceptions(Exception ex) {
        return buildErrorResponse(ex.getMessage());
    }

    private ProblemDetail buildErrorResponse(String message) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        return Map.of("message error", errors);
    }


}
