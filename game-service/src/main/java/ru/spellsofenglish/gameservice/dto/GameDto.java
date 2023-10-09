package ru.spellsofenglish.gameservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.spellsofenglish.gameservice.models.Player;

public record GameDto(
        @NotNull
        Player player,
        @NotNull
        @Min(value=0)
        int playerPosition
) {
}
