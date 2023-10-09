package ru.spellsofenglish.gameservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.client.PlayerClient;
import ru.spellsofenglish.gameservice.exception.InvalidDataException;
import ru.spellsofenglish.gameservice.mapper.ProgressMapperDto;
import ru.spellsofenglish.gameservice.models.Player;
import ru.spellsofenglish.gameservice.models.Progress;
import ru.spellsofenglish.gameservice.service.PlayerService;

import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerClient playerClient;
    private final ProgressMapperDto progressMapperDto;

    @Autowired
    public PlayerServiceImpl(PlayerClient playerClient, ProgressMapperDto progressMapperDto) {
        this.playerClient = playerClient;
        this.progressMapperDto = progressMapperDto;
    }

    @Override
    public Player getPlayer(UUID playerId) {
        return playerClient.getPlayerById(playerId)
                .orElseThrow(()->new InvalidDataException("Player with this id not found","Player not found"));
    }

    @Override
    public void updatePlayerTotalPoints(Integer totalPoints, UUID playerId) {
        Player player=getPlayer(playerId);
        Progress progress=player.getProgress();
        progress.setTotalPoints(totalPoints);
        playerClient.updatePlayer(playerId, progressMapperDto.apply(player.getProgress()));
    }

    @Override
    public void allowOrDenyMovePlayer(UUID playerId, boolean allowMove) {
        playerClient.allowOrDenyMovePlayer(playerId,allowMove);
    }

    @Override
    public void updatePlayerGameLevel(Integer gameLevel, UUID playerId) {
        Player player=getPlayer(playerId);
        Progress progress=player.getProgress();
        progress.setGameLevel(gameLevel);
        progress.setTotalPoints(0);
        playerClient.updatePlayer(playerId,progressMapperDto.apply(player.getProgress()));
    }
}
