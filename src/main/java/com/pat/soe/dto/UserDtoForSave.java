package com.pat.soe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import static com.pat.soe.service.impl.UserServiceImpl.*;

public record UserDtoForSave(
        @NotBlank(message = EMAIL_NOT_CORRECT)
        @Pattern(message = "Bad formed person username: ${validatedValue}",
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$")
        @Length(min = 2, max = 255, message = THE_LENGTH_OF_THE_NAME)
        @Email(message = EMAIL_NOT_CORRECT)
        String email,
        @NotBlank(message = PASSWORD_NOT_CORRECT)
        @Pattern(message = "Bad formed person password",
                regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=~`{}\\[\\]|\\\\:;'<>,.?\\/]{8,24}$")
        @Size(min = 8)
        String password,
        @NotBlank(message = NICKNAME_NOT_CORRECT)
        @Pattern(message = "Bad formed person username: ${validatedValue}",
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$")
        @Length(min = 2, max = 255, message = THE_LENGTH_OF_THE_NAME)
        String nickName) {
}
