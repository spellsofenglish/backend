package ru.spellsofenglish.player.dto.progress;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record ProgressDto(
        Double progress,
        @Min(value = 0, message = "Game level cannot be less than zero")
        @Max(value = 48, message = "The maximum level is 48")
        @NotNull(message = "Game level is not null")
        @JsonProperty(value = "game_level")
        Integer gameLevel,
        @NotNull(message = "Total point is not null")
        @JsonProperty(value = "total_points")
        Integer totalPoint

) {
}
