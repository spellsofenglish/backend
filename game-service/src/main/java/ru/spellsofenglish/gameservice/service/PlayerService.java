package ru.spellsofenglish.gameservice.service;

import ru.spellsofenglish.gameservice.models.Player;

public interface PlayerService {
    Player getPlayer();
    void updatePlayer(Integer gameLevel);
}
