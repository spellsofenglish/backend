package ru.spellsofenglish.gameservice.service;

import ru.spellsofenglish.gameservice.models.Player;

public interface PlayerService {
    Player getPlayer();
    void updatePlayerGameLevel(Integer gameLevel);
    void updatePlayerTotalPoints(Integer totalPoints);
}
