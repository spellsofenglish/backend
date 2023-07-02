package ru.spellsofenglish.authservise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import static ru.spellsofenglish.authservise.service.impl.UserServiceImpl.EMAIL_NOT_CORRECT;
import static ru.spellsofenglish.authservise.service.impl.UserServiceImpl.THE_LENGTH_OF_THE_NAME;

public record UserDtoForRecoveryPass(
        @NotBlank(message = EMAIL_NOT_CORRECT)
        @Length(min = 2, max = 255, message = THE_LENGTH_OF_THE_NAME)
        @Pattern(message = "Bad formed person username: ${validatedValue}",
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$")
        String email) {
}
