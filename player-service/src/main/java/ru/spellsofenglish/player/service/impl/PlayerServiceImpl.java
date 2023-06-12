package ru.spellsofenglish.player.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.mapper.PlayerMapperDto;
import ru.spellsofenglish.player.repository.PlayerRepository;
import ru.spellsofenglish.player.service.PlayerService;

import java.util.UUID;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerMapperDto playerMapperDto;
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerMapperDto playerMapperDto, PlayerRepository playerRepository) {
        this.playerMapperDto = playerMapperDto;
        this.playerRepository = playerRepository;
    }


    @Override
    public PlayerDto getPlayer(UUID id) {
        return playerMapperDto.apply(findPlayerById(id));
    }

    @Override
    public Player findPlayerById(UUID id) {
        return playerRepository.findPlayerById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not fount"));
    }

    @Override
    @Transactional
    public void createPlayer(PlayerDto playerDto, Progress progress) {
        if (!playerRepository.existsByUsername(playerDto.username())){
            var player = Player.builder()
                    .username(playerDto.username())
                    .progress(progress)
                    .build();
            playerRepository.save(player);
        } else {
            throw new IllegalArgumentException("The name " + playerDto.username() +" is already busy, try another one");
        }
    }


}
