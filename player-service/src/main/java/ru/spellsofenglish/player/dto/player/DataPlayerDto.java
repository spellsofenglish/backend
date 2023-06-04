package ru.spellsofenglish.player.dto.player;

public record DataPlayerDto(
        String username,
        Integer points,
        Long progress,
        Long game_level

) {
}
