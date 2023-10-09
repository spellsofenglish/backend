package ru.spellsofenglish.player.dto.progress;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record ProgressDto(
        @Min(value = 0, message = "Game level cannot be less than zero")
        @Max(value = 108, message = "The maximum level is 108")
        @NotNull(message = "Game level is not null")
        Integer gameLevel,
        @NotNull(message = "Total point is not null")
        @Min(value = -5, message = "The minimum number of points is -5")
        Integer totalPoints

) {
}
