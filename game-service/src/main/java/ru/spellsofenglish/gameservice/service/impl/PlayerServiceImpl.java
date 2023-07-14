package ru.spellsofenglish.gameservice.service.impl;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.client.PlayerClient;
import ru.spellsofenglish.gameservice.dto.ProgressDto;
import ru.spellsofenglish.gameservice.models.Player;
import ru.spellsofenglish.gameservice.models.Progress;
import ru.spellsofenglish.gameservice.service.PlayerService;

import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerClient playerClient;

    public PlayerServiceImpl(PlayerClient playerClient) {
        this.playerClient = playerClient;
    }

    @Override
    public Player getPlayer() {
        return playerClient.getPlayerById(UUID.fromString("242e710c-823d-4aa6-bd12-53d02885173b")).orElseThrow(()->new NullPointerException("Player not found"));
    }

    @Override
    public void updatePlayer(Integer gameLevel) {
        Player player=getPlayer();
        Progress progress=player.getProgress();
        progress.setGameLevel(progress.getGameLevel()+gameLevel);
        progress.setTotalPoint(0);
        playerClient.updatePlayer(UUID.fromString("242e710c-823d-4aa6-bd12-53d02885173b"),new ProgressDto(
                progress.getGameLevel(), progress.getTotalPoint()
        ));
    }
}
