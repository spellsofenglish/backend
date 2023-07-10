package ru.spellsofenglish.player.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.exception.InvalidDataException;
import ru.spellsofenglish.player.repository.PlayerRepository;
import ru.spellsofenglish.player.service.PlayerService;

import java.util.UUID;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findPlayerById(UUID id) {
        return playerRepository.findPlayerById(id)
                .orElseThrow(() -> new InvalidDataException("We didn't find such a player, try again (", "User not found"));
    }

    @Override
    @Transactional
    public Player createPlayer(@NotNull PlayerDto playerDto, Progress progress) {
        if (!playerRepository.existsByUsername(playerDto.username())) {
            var player = new Player();
            player.setUsername(playerDto.username());
            player.setProgress(progress);
            return playerRepository.save(player);
        } else {
            throw new InvalidDataException("The name " + playerDto.username() + " is already busy, try another one",
                    "A user with this name was found");
        }

    }

    @Override
    @Transactional
    public Player updatePlayer(UUID id, PlayerDto playerDto) {
        if (playerRepository.existsByUsername(playerDto.username())) {
            throw new InvalidDataException("The name " + playerDto.username() + " is already busy, try another one",
                    "A user with this name was found");
        }
        var player = findPlayerById(id);
        player.setUsername(playerDto.username());
        return playerRepository.save(player);
    }
}