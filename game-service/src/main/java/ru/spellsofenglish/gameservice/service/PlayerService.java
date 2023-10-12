package ru.spellsofenglish.gameservice.service;

import ru.spellsofenglish.gameservice.models.Player;

import java.util.UUID;

public interface PlayerService {
    Player getPlayer(UUID playerId);
    void updatePlayerGameLevel(Integer gameLevel, UUID playerId);
    void updatePlayerProgress(Integer totalPoints, UUID playerId);
    void allowOrDenyMovePlayer (UUID playerId, boolean allowMove);
}
