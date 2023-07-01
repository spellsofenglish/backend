package com.pat.soe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import static com.pat.soe.service.impl.UserServiceImpl.EMAIL_NOT_CORRECT;

public record UserDtoForSave(
        @NotNull(message = EMAIL_NOT_CORRECT)
        @Pattern(message = "Bad formed person username: ${validatedValue}",
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$")
        @Length(min = 2, max = 255, message = "The length of the name must be from 2 to 255 characters")
        @Email(message = EMAIL_NOT_CORRECT)
        String email,
        String password,
        String nickName) {
}
