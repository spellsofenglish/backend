package ru.spellsofenglish.authservise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import static ru.spellsofenglish.authservise.service.impl.UserServiceImpl.PASSWORD_NOT_CORRECT;


public record UserDtoForUpdatePass(
        UUID id,
        @NotBlank(message = PASSWORD_NOT_CORRECT)
        @Pattern(message = "Bad formed person password",
                regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=~`{}\\[\\]|\\\\:;'<>,.?\\/]{8,24}$")
        @Size(min = 8)
        String oldPassword,
        @NotBlank(message = PASSWORD_NOT_CORRECT)
        @Pattern(message = "Bad formed person password",
                regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=~`{}\\[\\]|\\\\:;'<>,.?\\/]{8,24}$")
        @Size(min = 8)
        String newPassword) {

}
