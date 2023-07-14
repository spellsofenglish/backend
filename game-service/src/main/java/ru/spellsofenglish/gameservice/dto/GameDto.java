package ru.spellsofenglish.gameservice.dto;

import ru.spellsofenglish.gameservice.models.Player;

public record GameDto(
        Player player,
        TaskDto taskDto
) {
}
