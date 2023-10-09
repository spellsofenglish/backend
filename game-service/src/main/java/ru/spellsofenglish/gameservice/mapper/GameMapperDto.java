package ru.spellsofenglish.gameservice.mapper;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.models.Game;
import ru.spellsofenglish.gameservice.service.PlayerService;

import java.util.function.Function;

@Service
public class GameMapperDto implements Function<Game, GameDto> {
    private final PlayerService playerService;

    public GameMapperDto(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public GameDto apply(Game game) {
        return new GameDto(
                playerService.getPlayer(game.getPlayerId()),
                game.getPlayerPosition()
        );

    }
}
