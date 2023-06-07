package ru.spellsofenglish.player.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.player.dto.player.DataPlayerDto;
import ru.spellsofenglish.player.dto.player.PlayerDto;
import ru.spellsofenglish.player.entity.Player;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.entity.Settings;
import ru.spellsofenglish.player.mapper.PlayerMapperDto;
import ru.spellsofenglish.player.repository.PlayerRepository;
import ru.spellsofenglish.player.service.PlayerService;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerMapperDto playerMapperDto;
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerMapperDto playerMapperDto, PlayerRepository playerRepository) {
        this.playerMapperDto = playerMapperDto;
        this.playerRepository = playerRepository;
    }


    @Override
    public DataPlayerDto getPlayer(String username) {
        return playerRepository.findByUsername(username)
                .map(playerMapperDto)
                .orElseThrow(() -> new IllegalArgumentException("User not fount"));
    }

    @Override
    @Transactional
    public void updatePlayer(PlayerDto playerDto, Settings settings, Progress progress) {
        var player = Player.builder()
                .username(playerDto.username())
                .points(50)
                .progress(progress)
                .settings(settings)
                .build();
        playerRepository.save(player);
    }
}
