package ru.spellsofenglish.gameservice.service.impl;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.service.GameService;
import ru.spellsofenglish.gameservice.service.PlayerService;
import ru.spellsofenglish.gameservice.service.TaskService;

@Service
public class GameServiceImpl implements GameService {
    private final PlayerService playerService;


    public GameServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public GameDto getGame() {
        return new GameDto(
                playerService.getPlayer()
        );
    }
}
