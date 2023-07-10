package ru.spellsofenglish.player.dto.player;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record PlayerDto(
        @NotNull(message = "User name is not null")
        @Pattern(message = "Bad formed person username: ${validatedValue}",
                regexp = "^[a-z A-Zа-яA-Я0-9_-]{2,255}$")
        @Length(min = 2, max = 255, message = "The length of the name must be from 2 to 255 characters")
        String username

) {
}
