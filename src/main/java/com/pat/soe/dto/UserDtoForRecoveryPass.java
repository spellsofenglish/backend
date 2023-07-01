package com.pat.soe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import static com.pat.soe.service.impl.UserServiceImpl.EMAIL_NOT_CORRECT;
import static com.pat.soe.service.impl.UserServiceImpl.THE_LENGTH_OF_THE_NAME;

public record UserDtoForRecoveryPass(
        @NotBlank(message = EMAIL_NOT_CORRECT)
        @Length(min = 2, max = 255, message = THE_LENGTH_OF_THE_NAME)
        @Email(message = EMAIL_NOT_CORRECT)
        String email) {
}
