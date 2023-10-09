package ru.spellsofenglish.gameservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AnswerTaskDto(
        @NotNull
        @Min(value = 0)
        int indexTask,
        @NotNull
        UUID playerId,
        @NotNull
        String word
) {
}
