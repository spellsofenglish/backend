package ru.spellsofenglish.gameservice.service.impl;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.exception.InvalidDataException;
import ru.spellsofenglish.gameservice.mapper.GameMapperDto;
import ru.spellsofenglish.gameservice.models.Game;
import ru.spellsofenglish.gameservice.repository.GameRepository;
import ru.spellsofenglish.gameservice.service.GameService;
import ru.spellsofenglish.gameservice.service.PlayerService;

import java.util.Random;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapperDto gameMapperDto;
    private final PlayerService playerService;


    public GameServiceImpl(GameRepository gameRepository, GameMapperDto gameMapperDto, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.gameMapperDto = gameMapperDto;
        this.playerService = playerService;
    }

    @Override
    public GameDto getGame(UUID playerId) {
        if (gameRepository.existsByPlayerId(playerId)) {
            return gameMapperDto.apply(gameRepository.findByPlayerId(playerId).get());
        } else {
            var game = Game.builder()
                    .playerId(playerId)
                    .playerPosition(0)
                    .build();
            gameRepository.save(game);
            return gameMapperDto.apply(game);
        }
    }

    @Override
    public void updateGame(UUID playerId, int numberDice) {
        var game = gameRepository.findByPlayerId(playerId);
        game.get().setPlayerPosition(game.get().getPlayerPosition() + numberDice);
        gameRepository.save(game.get());
    }

    @Override
    public void updateGameIfIsOver(UUID playerId) {
       var game= gameRepository.findByPlayerId(playerId);
       game.get().setPlayerPosition(0);
        playerService.updatePlayerProgress(40,playerId);
        playerService.updatePlayerGameLevel(0,playerId);
       gameRepository.save(game.get());
    }

    @Override
    public Integer rollDice(UUID playerId) {
        if(playerService.getPlayer(playerId).getProgress().getTotalPoints()!=0) {

            if (playerService.getPlayer(playerId).isAllowMove()) {
                Integer randomNumber = new Random().nextInt(12) + 2;
                playerService.updatePlayerGameLevel(randomNumber, playerId);
                playerService.allowOrDenyMovePlayer(playerId, false);
                updateGame(playerId, randomNumber);
                return randomNumber;
            }

            else {
                throw new InvalidDataException("You aren't moved, please finish your task", "You already moved");
            }

        }

        else{
            updateGameIfIsOver(playerId);
            throw new InvalidDataException("Your game is over", "Game is over");
        }
    }
}
