package ru.spellsofenglish.gameservice.service;

import ru.spellsofenglish.gameservice.dto.GameDto;

import java.util.UUID;

public interface GameService {
    GameDto getGame(UUID playerId);
    void updateGame(UUID playerId, int numberDice);
    void updateGameIfIsOver(UUID playerId);
    Integer rollDice(UUID playerId);
}
